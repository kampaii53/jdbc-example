CREATE TABLE IF NOT EXISTS public.account_types(   id bigint NOT NULL,    name character varying COLLATE pg_catalog."default",    CONSTRAINT account_types_pkey PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS public.users(    id bigint NOT NULL,    fullname character varying(255) COLLATE pg_catalog."default",    total_balance bigint,    CONSTRAINT users_pkey PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS public.settings(   id bigint NOT NULL,    name character varying COLLATE pg_catalog."default",    CONSTRAINT settings_pkey PRIMARY KEY (id));

