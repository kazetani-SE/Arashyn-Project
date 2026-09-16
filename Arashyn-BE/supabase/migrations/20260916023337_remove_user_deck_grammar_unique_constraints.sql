ALTER TABLE user_deck
DROP CONSTRAINT IF EXISTS user_deck_deck_id_user_id_key;

ALTER TABLE user_grammar
DROP CONSTRAINT IF EXISTS user_grammar_grammar_id_user_id_key;