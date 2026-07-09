-- ============================================================
-- grammar
-- ============================================================
CREATE INDEX idx_grammar_owner_id
    ON grammar(owner_id);

CREATE INDEX idx_grammar_owner_id_public
    ON grammar(owner_id)
    WHERE is_public = TRUE;

CREATE INDEX idx_grammar_language_public
    ON grammar(language)
    WHERE is_public = TRUE;

-- ============================================================
-- component
-- ============================================================
CREATE INDEX idx_component_grammar_id
    ON component(grammar_id);

CREATE INDEX idx_component_form_id_order
    ON component(form_id, "order");

CREATE INDEX idx_component_keyword_order
    ON component(keyword, "order");

-- ============================================================
-- meaning
-- ============================================================
CREATE INDEX idx_meaning_grammar_owner_id
    ON meaning(grammar_id, owner_id);

CREATE INDEX idx_meaning_owner_id
    ON meaning(owner_id);

CREATE INDEX idx_meaning_grammar_id_public
    ON meaning(grammar_id)
    WHERE is_public = TRUE;

-- ============================================================
-- example
-- ============================================================
CREATE INDEX idx_example_meaning_id_public
    ON example(meaning_id)
    WHERE is_public = TRUE;

CREATE INDEX idx_example_grammar_owner_id
    ON example(meaning_id, owner_id);

-- ============================================================
-- note
-- ============================================================
CREATE INDEX idx_note_grammar_id_public
    ON note(grammar_id)
    WHERE is_public = TRUE;

CREATE INDEX idx_note_owner_id
    ON note(owner_id);

CREATE INDEX idx_note_grammar_owner_id
    ON note(grammar_id, owner_id);

-- ============================================================
-- folder
-- ============================================================
CREATE INDEX idx_folder_parent_id
    ON folder(parent_id);

CREATE INDEX idx_folder_owner_id
    ON folder(owner_id);

CREATE INDEX idx_folder_parent_id_public
    ON folder(parent_id)
    WHERE is_public = TRUE;

CREATE INDEX idx_folder_owner_id_public
    ON folder(owner_id)
    WHERE is_public = TRUE;

-- ============================================================
-- deck
-- ============================================================
CREATE INDEX idx_deck_folder_id
    ON deck(folder_id);

CREATE INDEX idx_deck_owner_id
    ON deck(owner_id);

CREATE INDEX idx_deck_language
    ON deck(language);

-- ============================================================
-- user_tag
-- ============================================================
CREATE INDEX idx_user_tag_owner_id
    ON user_tag(owner_id);

-- ============================================================
-- deck_filter
-- ============================================================
CREATE INDEX idx_deck_filter_filter_id
    ON deck_filter(filter_id);

-- ============================================================
-- grammar_filter
-- ============================================================
CREATE INDEX idx_grammar_filter_filter_id
    ON grammar_filter(filter_id);

-- ============================================================
-- deck_grammar
-- ============================================================
CREATE INDEX idx_deck_grammar_grammar_id
    ON deck_grammar(grammar_id);

-- ============================================================
-- user_folder
-- ============================================================
CREATE INDEX idx_user_folder_user_id
    ON user_folder(user_id);

CREATE INDEX idx_user_folder_parent_id
    ON user_folder(parent_id);