create extension if not exists "pgcrypto";

create table if not exists pending_registrations (
    id                 uuid primary key default gen_random_uuid(),
    email              varchar(255) unique not null,
    verification_code  varchar(255) not null,   -- BCrypt hash of the 6-digit OTP
    payload            jsonb not null,           -- raw sign-up form fields
    expires_at         timestamptz not null,
    created_at         timestamptz not null default now()
    );

-- Speeds up "find pending row by email" during verification
create index if not exists idx_pending_registrations_email on pending_registrations (email);

-- Speeds up the cleanup job that purges expired rows
create index if not exists idx_pending_registrations_expires_at on pending_registrations (expires_at);