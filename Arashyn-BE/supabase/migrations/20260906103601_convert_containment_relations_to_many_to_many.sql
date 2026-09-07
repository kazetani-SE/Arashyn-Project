-- Converts deck-to-folder relationship from many-to-one to many-to-many.
ALTER TABLE deck
    DROP COLUMN IF EXISTS folder_id;

CREATE TABLE folder_deck
(
    folder_id UUID NOT NULL
        REFERENCES folder(id)
            ON DELETE CASCADE,

    deck_id UUID NOT NULL
        REFERENCES deck(id)
            ON DELETE CASCADE,

    PRIMARY KEY (folder_id, deck_id)
);

COMMENT ON TABLE folder_deck IS
    'Defines the many-to-many relationship between system folders and system decks.';


-- Converts system folder hierarchy from self-referencing many-to-one to many-to-many.
ALTER TABLE folder
    DROP COLUMN IF EXISTS parent_id;

CREATE TABLE folder_hierarchy
(
    parent_id UUID NOT NULL
        REFERENCES folder(id)
            ON DELETE CASCADE,

    child_id UUID NOT NULL
        REFERENCES folder(id)
            ON DELETE CASCADE,

    PRIMARY KEY (parent_id, child_id),

    CHECK (parent_id <> child_id)
);

COMMENT ON TABLE folder_hierarchy IS
    'Defines the parent-child hierarchy between system folders.';


-- Converts user deck-to-folder relationship from many-to-one to many-to-many.
ALTER TABLE user_deck
    DROP COLUMN IF EXISTS parent_id;

CREATE TABLE user_folder_deck
(
    user_folder_id UUID NOT NULL
        REFERENCES user_folder(id)
            ON DELETE CASCADE,

    user_deck_id UUID NOT NULL
        REFERENCES user_deck(id)
            ON DELETE CASCADE,

    PRIMARY KEY (user_folder_id, user_deck_id)
);

COMMENT ON TABLE user_folder_deck IS
    'Defines the many-to-many relationship between user folders and user decks.';


-- Converts user grammar-to-deck relationship from many-to-one to many-to-many.
ALTER TABLE user_grammar
    DROP COLUMN IF EXISTS parent_id;

CREATE TABLE user_deck_grammar
(
    user_deck_id UUID NOT NULL
        REFERENCES user_deck(id)
            ON DELETE CASCADE,

    user_grammar_id UUID NOT NULL
        REFERENCES user_grammar(id)
            ON DELETE CASCADE,

    PRIMARY KEY (user_deck_id, user_grammar_id)
);

COMMENT ON TABLE user_deck_grammar IS
    'Defines the many-to-many relationship between user decks and user grammars.';


-- Converts user folder hierarchy from self-referencing many-to-one to many-to-many.
ALTER TABLE user_folder
    DROP COLUMN IF EXISTS parent_id;

CREATE TABLE user_folder_hierarchy
(
    parent_id UUID NOT NULL
        REFERENCES user_folder(id)
            ON DELETE CASCADE,

    child_id UUID NOT NULL
        REFERENCES user_folder(id)
            ON DELETE CASCADE,

    PRIMARY KEY (parent_id, child_id),

    CHECK (parent_id <> child_id)
);

COMMENT ON TABLE user_folder_hierarchy IS
    'Defines the parent-child hierarchy between user folders.';


-- Adds a reverse-direction index for deck-to-filter lookups.
CREATE INDEX IF NOT EXISTS idx_deck_filter_filter_id
    ON deck_filter (filter_id, deck_id);


-- Adds a reverse-direction index for grammar-to-filter lookups.
CREATE INDEX IF NOT EXISTS idx_grammar_filter_filter_id
    ON grammar_filter (filter_id, grammar_id);


-- Adds a reverse-direction index for grammar-to-deck lookups.
CREATE INDEX IF NOT EXISTS idx_deck_grammar_grammar_id
    ON deck_grammar (grammar_id, deck_id);


-- Adds a reverse-direction index for deck-to-folder lookups.
CREATE INDEX IF NOT EXISTS idx_folder_deck_deck_id
    ON folder_deck (deck_id, folder_id);


-- Adds a reverse-direction index for child-to-parent folder lookups.
CREATE INDEX IF NOT EXISTS idx_folder_hierarchy_child_id
    ON folder_hierarchy (child_id, parent_id);


-- Adds a reverse-direction index for user deck-to-folder lookups.
CREATE INDEX IF NOT EXISTS idx_user_folder_deck_user_deck_id
    ON user_folder_deck (user_deck_id, user_folder_id);


-- Adds a reverse-direction index for user grammar-to-deck lookups.
CREATE INDEX IF NOT EXISTS idx_user_deck_grammar_user_grammar_id
    ON user_deck_grammar (user_grammar_id, user_deck_id);


-- Adds a reverse-direction index for child-to-parent user folder lookups.
CREATE INDEX IF NOT EXISTS idx_user_folder_hierarchy_child_id
    ON user_folder_hierarchy (child_id, parent_id);