-- Add email column
ALTER TABLE public.account
    ADD COLUMN email TEXT;

-- Backfill email from Supabase Auth
UPDATE public.account AS a
SET email = au.email
    FROM auth.users AS au
WHERE a.id = au.id;

-- Every account must have an email
ALTER TABLE public.account
    ALTER COLUMN email SET NOT NULL;

-- Emails should be unique regardless of letter casing
CREATE UNIQUE INDEX uq_account_email_lower
    ON public.account (LOWER(email));