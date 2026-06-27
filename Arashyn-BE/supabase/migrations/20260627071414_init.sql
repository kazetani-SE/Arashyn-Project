-- ============================================================
-- Extensions
-- ============================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ============================================================
-- Enum Types
-- ============================================================

CREATE TYPE account_role AS ENUM
    (
    'guest',
    'user',
    'contributor',
    'admin'
);

-- ============================================================
-- Account
-- Profile table (Supabase Auth)
-- ============================================================

CREATE TABLE account
(
    id UUID PRIMARY KEY
        REFERENCES auth.users(id)
            ON DELETE CASCADE,

    username VARCHAR(50) NOT NULL UNIQUE,

    avatar TEXT,

    role account_role NOT NULL DEFAULT 'user',

    is_banned BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE account IS 'Application user profile.';
COMMENT ON COLUMN account.id IS 'Reference to auth.users.id';


-- ============================================================
-- Form
-- ============================================================

CREATE TABLE form
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(50) NOT NULL,

    type VARCHAR(50) NOT NULL,

    language VARCHAR(5) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    UNIQUE(name, type, language)
);

COMMENT ON TABLE form IS 'Component forms (noun, verb, particle...).';


-- ============================================================
-- Grammar
-- ============================================================

CREATE TABLE grammar
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    title VARCHAR(100) NOT NULL,

    language VARCHAR(5) NOT NULL,

    owner_id UUID
        REFERENCES account(id)
                           ON DELETE SET NULL,

    is_public BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE grammar IS 'Grammar definition.';


-- ============================================================
-- Component
-- ============================================================

CREATE TABLE component
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    grammar_id UUID NOT NULL
        REFERENCES grammar(id)
            ON DELETE CASCADE,

    form_id UUID
                    REFERENCES form(id)
                        ON DELETE SET NULL,

    "order" INTEGER NOT NULL,

    keyword VARCHAR(50),

    optional BOOLEAN NOT NULL DEFAULT FALSE,

    group_key SMALLINT NOT NULL DEFAULT 0
);

COMMENT ON TABLE component IS 'Grammar components.';


-- ============================================================
-- Meaning
-- ============================================================

CREATE TABLE meaning
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    grammar_id UUID NOT NULL
        REFERENCES grammar(id)
            ON DELETE CASCADE,

    owner_id UUID
                    REFERENCES account(id)
                        ON DELETE SET NULL,

    content TEXT NOT NULL,

    group_key SMALLINT NOT NULL DEFAULT 0,

    is_public BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE meaning IS 'Grammar meanings and usages.';


-- ============================================================
-- Example
-- ============================================================

CREATE TABLE example
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    meaning_id UUID NOT NULL
        REFERENCES meaning(id)
            ON DELETE CASCADE,

    owner_id UUID
                    REFERENCES account(id)
                        ON DELETE SET NULL,

    sentence TEXT NOT NULL,

    translation TEXT,

    note TEXT,

    group_key SMALLINT NOT NULL DEFAULT 0,

    is_public BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE example IS 'Example sentences.';


-- ============================================================
-- Note
-- ============================================================

CREATE TABLE note
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    grammar_id UUID NOT NULL
        REFERENCES grammar(id)
            ON DELETE CASCADE,

    owner_id UUID
                    REFERENCES account(id)
                        ON DELETE SET NULL,

    content TEXT NOT NULL,

    group_key SMALLINT NOT NULL DEFAULT 0,

    is_public BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE note IS 'Grammar notes.';

-- ============================================================
-- Folder
-- ============================================================

CREATE TABLE folder
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    parent_id UUID
        REFERENCES folder(id)
            ON DELETE CASCADE,

    name VARCHAR(50) NOT NULL,

    owner_id UUID
                     REFERENCES account(id)
                         ON DELETE SET NULL,

    is_public BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE folder IS 'Folder hierarchy for organizing decks.';


-- ============================================================
-- Deck
-- ============================================================

CREATE TABLE deck
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    folder_id UUID
                           REFERENCES folder(id)
                               ON DELETE SET NULL,

    name VARCHAR(50) NOT NULL,

    description VARCHAR(250),

    language VARCHAR(5) NOT NULL,

    owner_id UUID
                           REFERENCES account(id)
                               ON DELETE SET NULL,

    is_public BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE deck IS 'Grammar deck.';


-- ============================================================
-- User Tag
-- ============================================================

CREATE TABLE user_tag
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    owner_id UUID NOT NULL
        REFERENCES account(id)
            ON DELETE CASCADE,

    name VARCHAR(50) NOT NULL,

    language VARCHAR(5) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    UNIQUE(owner_id, name, language)
);

COMMENT ON TABLE user_tag IS 'User defined tags.';


-- ============================================================
-- System Filter
-- ============================================================

CREATE TABLE system_filter
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(50) NOT NULL,

    language VARCHAR(5) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    UNIQUE(name, language)
);

COMMENT ON TABLE system_filter IS 'Built-in grammar filters.';


-- ============================================================
-- Deck Filter
-- ============================================================

CREATE TABLE deck_filter
(
    deck_id UUID NOT NULL
        REFERENCES deck(id)
            ON DELETE CASCADE,

    filter_id UUID NOT NULL
        REFERENCES user_tag(id)
            ON DELETE CASCADE,

    PRIMARY KEY(deck_id, filter_id)
);

COMMENT ON TABLE deck_filter IS 'Many-to-many relationship between deck and user tags.';


-- ============================================================
-- Grammar Filter
-- ============================================================

CREATE TABLE grammar_filter
(
    grammar_id UUID NOT NULL
        REFERENCES grammar(id)
            ON DELETE CASCADE,

    filter_id UUID NOT NULL
        REFERENCES system_filter(id)
            ON DELETE CASCADE,

    PRIMARY KEY(grammar_id, filter_id)
);

COMMENT ON TABLE grammar_filter IS 'Many-to-many relationship between grammar and system filters.';


-- ============================================================
-- Deck Grammar
-- ============================================================

CREATE TABLE deck_grammar
(
    deck_id UUID NOT NULL
        REFERENCES deck(id)
            ON DELETE CASCADE,

    grammar_id UUID NOT NULL
        REFERENCES grammar(id)
            ON DELETE CASCADE,

    PRIMARY KEY(deck_id, grammar_id)
);

COMMENT ON TABLE deck_grammar IS 'Many-to-many relationship between deck and grammar.';

-- ============================================================
-- User Folder
-- ============================================================

CREATE TABLE user_folder
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    folder_id UUID NOT NULL
        REFERENCES folder(id)
            ON DELETE CASCADE,

    user_id UUID NOT NULL
        REFERENCES account(id)
            ON DELETE CASCADE,

    parent_id UUID
        REFERENCES user_folder(id)
            ON DELETE CASCADE,

    name VARCHAR(50) NOT NULL,

    stage SMALLINT NOT NULL DEFAULT 0,

    proficiency SMALLINT NOT NULL DEFAULT 0,

    last_open_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    UNIQUE(folder_id, user_id)
);

COMMENT ON TABLE user_folder IS
'User specific folder progress.';