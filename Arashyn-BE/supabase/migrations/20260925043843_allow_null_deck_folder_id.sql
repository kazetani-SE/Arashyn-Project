ALTER TABLE user_deck
    ALTER COLUMN deck_id DROP NOT NULL;

ALTER TABLE user_folder
    ALTER COLUMN folder_id DROP NOT NULL;