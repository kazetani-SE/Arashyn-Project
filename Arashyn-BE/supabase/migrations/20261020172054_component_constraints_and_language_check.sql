-- ============================================================
-- Migration: Add component XOR constraint, RESTRICT foreign key
-- on component.form_id, and language code validation.
-- ============================================================

-- 1. Ensure foreign key on component.form_id is RESTRICT (not SET NULL)
ALTER TABLE component
    DROP CONSTRAINT IF EXISTS component_form_id_fkey;

ALTER TABLE component
    ADD CONSTRAINT component_form_id_fkey
        FOREIGN KEY (form_id)
            REFERENCES form(id)
            ON DELETE RESTRICT;

-- 2. Component XOR: exactly one of form_id or keyword, keyword non-empty
ALTER TABLE component
    ADD CONSTRAINT chk_component_xor_form_or_keyword
        CHECK ((form_id IS NOT NULL) <> (keyword IS NOT NULL AND trim(keyword) <> ''));

-- 3. Language check constraints: VI, EN, JA, KO, ZH
ALTER TABLE form
    ADD CONSTRAINT chk_form_language
        CHECK (language IN ('VI', 'EN', 'JA', 'KO', 'ZH'));

ALTER TABLE grammar
    ADD CONSTRAINT chk_grammar_language
        CHECK (language IN ('VI', 'EN', 'JA', 'KO', 'ZH'));

ALTER TABLE deck
    ADD CONSTRAINT chk_deck_language
        CHECK (language IN ('VI', 'EN', 'JA', 'KO', 'ZH'));

ALTER TABLE user_tag
    ADD CONSTRAINT chk_user_tag_language
        CHECK (language IN ('VI', 'EN', 'JA', 'KO', 'ZH'));
