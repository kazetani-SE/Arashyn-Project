-- ============================================================
-- Migration: Drop stored "stage" (tree depth); derive it on read
--
-- Context: "stage" here is the *depth level* of a node in a hierarchy
-- (folder tree, or a deck's placement under a folder) — NOT a learning
-- progress indicator. "proficiency" is the real progress metric.
--
-- folder_hierarchy / user_folder_hierarchy are many-to-many, i.e. a
-- node can have more than one parent. Under a DAG, "depth" is not a
-- single fixed number — it depends on which ancestor path you take.
-- An app-supplied, DB-unenforced "stage" column would therefore be
-- ambiguous and prone to drifting out of sync whenever the tree
-- changes. It is dropped entirely and replaced with a function that
-- computes depth on demand, relative to a chosen root.
--
-- 1. Drops "stage" and folder-level progress from user_folder.
-- 2. Drops "stage" from user_deck (removed, not relocated).
-- 3. Adds cycle-prevention triggers on folder_hierarchy and
--    user_folder_hierarchy — still needed regardless of the "stage"
--    decision, since an indirect cycle breaks any recursive query on
--    these tables, including the depth functions below.
-- 4. Adds get_folder_depths() / get_user_folder_depths(): given a root
--    folder, returns every descendant with its depth relative to that
--    root — the on-demand replacement for a stored "stage".
-- ============================================================


-- ------------------------------------------------------------
-- 1. Remove folder-level level/progress tracking
-- ------------------------------------------------------------
ALTER TABLE user_folder
DROP COLUMN IF EXISTS stage,
    DROP COLUMN IF EXISTS proficiency,
    DROP COLUMN IF EXISTS last_open_at;

COMMENT ON TABLE user_folder IS
    'User-specific folder instance (personal naming/organization only). Depth and progress are not stored; both are derived on read.';


-- ------------------------------------------------------------
-- 2. Remove "stage" from user_deck (dropped, not relocated)
-- ------------------------------------------------------------
ALTER TABLE user_deck
DROP COLUMN IF EXISTS stage;


-- ------------------------------------------------------------
-- 3a. Cycle prevention for folder_hierarchy (system folders)
-- ------------------------------------------------------------
CREATE OR REPLACE FUNCTION prevent_folder_hierarchy_cycle()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.parent_id = NEW.child_id THEN
        RAISE EXCEPTION 'folder_hierarchy: parent_id and child_id cannot be equal';
END IF;

    IF EXISTS (
        WITH RECURSIVE ancestors AS (
            SELECT parent_id FROM folder_hierarchy WHERE child_id = NEW.parent_id
            UNION ALL
            SELECT fh.parent_id
            FROM folder_hierarchy fh
            JOIN ancestors a ON fh.child_id = a.parent_id
        )
        SELECT 1 FROM ancestors WHERE parent_id = NEW.child_id
    ) THEN
        RAISE EXCEPTION 'folder_hierarchy: inserting (parent=%, child=%) would create a cycle', NEW.parent_id, NEW.child_id;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_prevent_folder_hierarchy_cycle ON folder_hierarchy;
CREATE TRIGGER trg_prevent_folder_hierarchy_cycle
    BEFORE INSERT OR UPDATE ON folder_hierarchy
                         FOR EACH ROW EXECUTE FUNCTION prevent_folder_hierarchy_cycle();


-- ------------------------------------------------------------
-- 3b. Cycle prevention for user_folder_hierarchy (user folders)
-- ------------------------------------------------------------
CREATE OR REPLACE FUNCTION prevent_user_folder_hierarchy_cycle()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.parent_id = NEW.child_id THEN
        RAISE EXCEPTION 'user_folder_hierarchy: parent_id and child_id cannot be equal';
END IF;

    IF EXISTS (
        WITH RECURSIVE ancestors AS (
            SELECT parent_id FROM user_folder_hierarchy WHERE child_id = NEW.parent_id
            UNION ALL
            SELECT ufh.parent_id
            FROM user_folder_hierarchy ufh
            JOIN ancestors a ON ufh.child_id = a.parent_id
        )
        SELECT 1 FROM ancestors WHERE parent_id = NEW.child_id
    ) THEN
        RAISE EXCEPTION 'user_folder_hierarchy: inserting (parent=%, child=%) would create a cycle', NEW.parent_id, NEW.child_id;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_prevent_user_folder_hierarchy_cycle ON user_folder_hierarchy;
CREATE TRIGGER trg_prevent_user_folder_hierarchy_cycle
    BEFORE INSERT OR UPDATE ON user_folder_hierarchy
                         FOR EACH ROW EXECUTE FUNCTION prevent_user_folder_hierarchy_cycle();


-- ------------------------------------------------------------
-- 4a. On-demand depth computation: system folder tree
-- ------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_folder_depths(p_root_folder_id UUID)
RETURNS TABLE(folder_id UUID, depth INT) AS $$
    WITH RECURSIVE tree AS (
        SELECT p_root_folder_id AS folder_id, 0 AS depth
        UNION ALL
        SELECT fh.child_id, t.depth + 1
        FROM folder_hierarchy fh
        JOIN tree t ON fh.parent_id = t.folder_id
    )
SELECT * FROM tree;
$$ LANGUAGE sql STABLE;

COMMENT ON FUNCTION get_folder_depths(UUID) IS
    'Returns p_root_folder_id (depth 0) and every descendant, with depth relative to that root. Replaces a stored "stage" column, since depth is ambiguous when a folder can have multiple parents.';


-- ------------------------------------------------------------
-- 4b. On-demand depth computation: user folder tree
-- ------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_user_folder_depths(p_root_user_folder_id UUID)
RETURNS TABLE(user_folder_id UUID, depth INT) AS $$
    WITH RECURSIVE tree AS (
        SELECT p_root_user_folder_id AS user_folder_id, 0 AS depth
        UNION ALL
        SELECT ufh.child_id, t.depth + 1
        FROM user_folder_hierarchy ufh
        JOIN tree t ON ufh.parent_id = t.user_folder_id
    )
SELECT * FROM tree;
$$ LANGUAGE sql STABLE;

COMMENT ON FUNCTION get_user_folder_depths(UUID) IS
    'Returns p_root_user_folder_id (depth 0) and every descendant, with depth relative to that root. Replaces a stored "stage" column, since depth is ambiguous when a folder can have multiple parents.';