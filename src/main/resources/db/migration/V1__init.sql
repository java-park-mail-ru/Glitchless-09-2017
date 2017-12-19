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

CREATE TABLE score
(
  id     SERIAL  NOT NULL
    CONSTRAINT score_id_pk
    PRIMARY KEY,
  score  INTEGER NOT NULL,
  userid INTEGER NOT NULL
    CONSTRAINT score_users_id_fk
    REFERENCES users
);

CREATE UNIQUE INDEX score_id_uindex
  ON score (id);

CREATE INDEX score_score_index
  ON score (score);

