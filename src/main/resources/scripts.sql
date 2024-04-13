DROP DATABASE "user-management-db";

CREATE DATABASE "user-management-db" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';

CREATE SCHEMA public;

CREATE TABLE public.users
(
    id           uuid      NOT NULL,
    "name"       varchar   NOT NULL,
    email        varchar   NOT NULL,
    "password"   varchar   NOT NULL,
    created_date timestamp NOT NULL,
    last_update  timestamp NOT NULL,
    last_login   timestamp NOT NULL,
    is_active    bool      NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_un UNIQUE (email)
);

CREATE TABLE public.phones
(
    id           uuid      NOT NULL,
    user_id      uuid      NOT NULL,
    "number"     varchar   NOT NULL,
    city_code    varchar   NOT NULL,
    country_code varchar   NOT NULL,
    created_date timestamp NOT NULL,
    last_update  timestamp NOT NULL,
    CONSTRAINT phones_pk PRIMARY KEY (id),
    CONSTRAINT phones_fk FOREIGN KEY (user_id) REFERENCES public.users (id)
);