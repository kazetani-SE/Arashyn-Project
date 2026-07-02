-- ============================================================
-- User Deck
-- User-specific deck progress
-- ============================================================

CREATE TABLE user_deck
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    deck_id UUID NOT NULL
        REFERENCES deck(id)
            ON DELETE CASCADE,

    user_id UUID NOT NULL
        REFERENCES account(id)
            ON DELETE CASCADE,

    parent_id UUID
        REFERENCES user_folder(id)
            ON DELETE CASCADE,

    name VARCHAR(200) NOT NULL,

    stage SMALLINT NOT NULL DEFAULT 0,

    proficiency SMALLINT NOT NULL DEFAULT 0,

    last_open_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    UNIQUE(deck_id, user_id)
);

COMMENT ON TABLE user_deck IS
'User specific deck progress.';


-- ============================================================
-- User Grammar
-- User-specific grammar progress
-- ============================================================

CREATE TABLE user_grammar
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    grammar_id UUID NOT NULL
        REFERENCES grammar(id)
            ON DELETE CASCADE,

    user_id UUID NOT NULL
        REFERENCES account(id)
            ON DELETE CASCADE,

    parent_id UUID
        REFERENCES user_deck(id)
            ON DELETE CASCADE,

    name VARCHAR(250) NOT NULL,

    proficiency SMALLINT NOT NULL DEFAULT 0,

    last_review_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    UNIQUE(grammar_id, user_id)
);

COMMENT ON TABLE user_grammar IS
'User specific grammar progress.';