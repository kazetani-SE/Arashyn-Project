-- ============================================================
-- Migration: Clone model for user_folder / user_deck + source-only
-- sync-drift detection (folder/deck), on top of the many-to-many
-- schema (folder_hierarchy, folder_deck, deck_grammar,
-- user_folder_hierarchy, user_folder_deck, user_deck_grammar).
--
-- Summary of the model:
--   - user_folder / user_deck are independent clones. folder_id /
--     deck_id become soft pointers only: nullable, no FK, used
--     solely to locate the source for sync. Source can be
--     hard-deleted at any time without touching the clone.
--   - No is_public on user_folder / user_deck — clones have no
--     public/private concept of their own.
--   - user_deck gets description + language copied from deck, so
--     the clone is fully self-contained (name already existed).
--   - Sync-drift detection, source-only:
--       * folder.children_version / deck.children_version: counter
--         on the SOURCE row, bumped by trigger only when that row's
--         direct children change via folder_hierarchy / folder_deck
--         / deck_grammar (never via the user_* equivalents).
--       * user_folder.synced_version / user_deck.synced_version:
--         the source's children_version at last clone/sync. A cheap
--         "!=" check tells whether anything changed at all.
--       * user_folder_sync_base / user_deck_sync_base: a single
--         current snapshot (not history) of the source's direct
--         children at last clone/sync. On mismatch, diff is
--         computed as (source now − base) = added,
--         (base − source now) = removed — never reads the user's
--         current tree, so it is blind to the user's own edits.
--         On every sync, the old snapshot rows are deleted and
--         replaced by the new one; nothing accumulates.
-- ============================================================


-- ------------------------------------------------------------
-- 1. Source-side version counters
-- ------------------------------------------------------------
ALTER TABLE folder
    ADD COLUMN IF NOT EXISTS children_version INT NOT NULL DEFAULT 0;

ALTER TABLE deck
    ADD COLUMN IF NOT EXISTS children_version INT NOT NULL DEFAULT 0;

COMMENT ON COLUMN folder.children_version IS
    'Bumped whenever this folder''s direct children change on the source side (folder_hierarchy or folder_deck row inserted/deleted). Never affected by user_folder_hierarchy / user_folder_deck.';
COMMENT ON COLUMN deck.children_version IS
    'Bumped whenever this deck''s grammar set changes on the source side (deck_grammar row inserted/deleted). Never affected by user_deck_grammar.';


-- ------------------------------------------------------------
-- 2. Triggers: bump children_version, source side only
-- ------------------------------------------------------------
CREATE OR REPLACE FUNCTION bump_folder_version_on_hierarchy()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
UPDATE folder SET children_version = children_version + 1 WHERE id = NEW.parent_id;
RETURN NEW;
END IF;
    IF TG_OP = 'DELETE' THEN
UPDATE folder SET children_version = children_version + 1 WHERE id = OLD.parent_id;
RETURN OLD;
END IF;
RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_bump_folder_version_on_hierarchy ON folder_hierarchy;
CREATE TRIGGER trg_bump_folder_version_on_hierarchy
    AFTER INSERT OR DELETE ON folder_hierarchy
    FOR EACH ROW EXECUTE FUNCTION bump_folder_version_on_hierarchy();


CREATE OR REPLACE FUNCTION bump_folder_version_on_folder_deck()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
UPDATE folder SET children_version = children_version + 1 WHERE id = NEW.folder_id;
RETURN NEW;
END IF;
    IF TG_OP = 'DELETE' THEN
UPDATE folder SET children_version = children_version + 1 WHERE id = OLD.folder_id;
RETURN OLD;
END IF;
RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_bump_folder_version_on_folder_deck ON folder_deck;
CREATE TRIGGER trg_bump_folder_version_on_folder_deck
    AFTER INSERT OR DELETE ON folder_deck
    FOR EACH ROW EXECUTE FUNCTION bump_folder_version_on_folder_deck();


CREATE OR REPLACE FUNCTION bump_deck_version_on_deck_grammar()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
UPDATE deck SET children_version = children_version + 1 WHERE id = NEW.deck_id;
RETURN NEW;
END IF;
    IF TG_OP = 'DELETE' THEN
UPDATE deck SET children_version = children_version + 1 WHERE id = OLD.deck_id;
RETURN OLD;
END IF;
RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_bump_deck_version_on_deck_grammar ON deck_grammar;
CREATE TRIGGER trg_bump_deck_version_on_deck_grammar
    AFTER INSERT OR DELETE ON deck_grammar
    FOR EACH ROW EXECUTE FUNCTION bump_deck_version_on_deck_grammar();


-- ------------------------------------------------------------
-- 3. user_folder: soft pointer to source, drop old constraints,
--    add synced_version
-- ------------------------------------------------------------
ALTER TABLE user_folder
DROP CONSTRAINT IF EXISTS user_folder_folder_id_fkey;

ALTER TABLE user_folder
    ALTER COLUMN folder_id DROP NOT NULL;

ALTER TABLE user_folder
DROP CONSTRAINT IF EXISTS user_folder_folder_id_user_id_key;

ALTER TABLE user_folder
    ADD COLUMN IF NOT EXISTS synced_version INT NOT NULL DEFAULT 0;

COMMENT ON COLUMN user_folder.folder_id IS
    'Soft pointer to the source folder, nullable, no FK. Source may be hard-deleted; used only to locate the source for sync.';
COMMENT ON COLUMN user_folder.synced_version IS
    'folder.children_version of the source at last clone/sync. Mismatch with the current source value means the source''s children changed since; the actual diff is read from user_folder_sync_base.';


-- ------------------------------------------------------------
-- 4. user_deck: soft pointer to source, self-contained attributes,
--    drop old constraints, add synced_version
-- ------------------------------------------------------------
ALTER TABLE user_deck
DROP CONSTRAINT IF EXISTS user_deck_deck_id_fkey;

ALTER TABLE user_deck
    ALTER COLUMN deck_id DROP NOT NULL;

ALTER TABLE user_deck
DROP CONSTRAINT IF EXISTS user_deck_deck_id_user_id_key;

ALTER TABLE user_deck
    ADD COLUMN IF NOT EXISTS description VARCHAR(250),
    ADD COLUMN IF NOT EXISTS language VARCHAR(5),
    ADD COLUMN IF NOT EXISTS synced_version INT NOT NULL DEFAULT 0;

COMMENT ON COLUMN user_deck.deck_id IS
    'Soft pointer to the source deck, nullable, no FK. Source may be hard-deleted; used only to locate the source for sync.';
COMMENT ON COLUMN user_deck.synced_version IS
    'deck.children_version of the source at last clone/sync. Mismatch with the current source value means the source''s grammar set changed since; the actual diff is read from user_deck_sync_base.';


-- ------------------------------------------------------------
-- 5. Sync base snapshot tables (current snapshot only, not history;
--    every sync replaces the rows wholesale, nothing accumulates)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS user_folder_sync_base
(
    user_folder_id UUID NOT NULL
    REFERENCES user_folder(id)
    ON DELETE CASCADE,

    -- 'folder' or 'deck': a source folder's direct children are
    -- both subfolders (folder_hierarchy) and decks (folder_deck).
    child_kind VARCHAR(10) NOT NULL
    CHECK (child_kind IN ('folder', 'deck')),

    -- source folder_id or deck_id; no FK — must still be
    -- comparable even after the source child is hard-deleted.
    child_id UUID NOT NULL,

    PRIMARY KEY (user_folder_id, child_kind, child_id)
    );

COMMENT ON TABLE user_folder_sync_base IS
    'Current snapshot (not history) of the source folder''s direct children at the last clone/sync of this user_folder. Replaced wholesale on every sync. Used to compute added/removed purely from source-side changes, independent of the user''s own user_folder_hierarchy / user_folder_deck edits.';

CREATE TABLE IF NOT EXISTS user_deck_sync_base
(
    user_deck_id UUID NOT NULL
    REFERENCES user_deck(id)
    ON DELETE CASCADE,

    -- source grammar_id; no FK, source deck_grammar row may be gone.
    grammar_id UUID NOT NULL,

    PRIMARY KEY (user_deck_id, grammar_id)
    );

COMMENT ON TABLE user_deck_sync_base IS
    'Current snapshot (not history) of the source deck''s grammar set at the last clone/sync of this user_deck. Replaced wholesale on every sync. Used to compute added/removed purely from source-side changes, independent of the user''s own user_deck_grammar edits.';


-- ------------------------------------------------------------
-- 6. Backfill existing clones: baseline "as of now" for clones
--    whose source is still alive. Clones whose source is already
--    gone (folder_id / deck_id NULL or dangling) get no base rows
--    and keep synced_version = 0 — nothing to compare against.
-- ------------------------------------------------------------
INSERT INTO user_folder_sync_base (user_folder_id, child_kind, child_id)
SELECT uf.id, 'folder', fh.child_id
FROM user_folder uf
         JOIN folder_hierarchy fh ON fh.parent_id = uf.folder_id
WHERE uf.folder_id IS NOT NULL
    ON CONFLICT DO NOTHING;

INSERT INTO user_folder_sync_base (user_folder_id, child_kind, child_id)
SELECT uf.id, 'deck', fd.deck_id
FROM user_folder uf
         JOIN folder_deck fd ON fd.folder_id = uf.folder_id
WHERE uf.folder_id IS NOT NULL
    ON CONFLICT DO NOTHING;

INSERT INTO user_deck_sync_base (user_deck_id, grammar_id)
SELECT ud.id, dg.grammar_id
FROM user_deck ud
         JOIN deck_grammar dg ON dg.deck_id = ud.deck_id
WHERE ud.deck_id IS NOT NULL
    ON CONFLICT DO NOTHING;

UPDATE user_deck ud
SET description    = d.description,
    language       = d.language,
    synced_version = d.children_version
    FROM deck d
WHERE ud.deck_id = d.id;

UPDATE user_folder uf
SET synced_version = f.children_version
    FROM folder f
WHERE uf.folder_id = f.id;