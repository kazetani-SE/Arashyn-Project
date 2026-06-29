create table public.account_sessions (
                                         account_id uuid primary key
                                             references public.account(id)
                                                 on delete cascade,

                                         refresh_token text not null,

                                         created_at timestamptz not null default now(),

                                         updated_at timestamptz not null default now()
);