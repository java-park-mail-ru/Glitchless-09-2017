CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE public.users
(
  id       SERIAL PRIMARY KEY NOT NULL,
  login    CITEXT             NOT NULL,
  password TEXT               NOT NULL,
  email    CITEXT
);

CREATE UNIQUE INDEX users_id_uindex
  ON public.users (id);
CREATE UNIQUE INDEX users_login_uindex
  ON public.users (login);
CREATE UNIQUE INDEX users_email_uindex
  ON public.users (email);

CREATE TABLE users
(
  id       SERIAL NOT NULL
    CONSTRAINT users_pkey
    PRIMARY KEY,
  login    CITEXT NOT NULL,
  password TEXT   NOT NULL,
  email    CITEXT
);

CREATE UNIQUE INDEX users_id_uindex
  ON users (id);

CREATE UNIQUE INDEX users_login_uindex
  ON users (login);

CREATE UNIQUE INDEX users_email_uindex
  ON users (email);

