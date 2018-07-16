CREATE TABLE IF NOT EXISTS events (
  id UUID NOT NULL PRIMARY KEY,
  name VARCHAR(255),
  description text,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS raffles (
  id UUID NOT NULL PRIMARY KEY,
  name VARCHAR(255),
  noWinners INT,
  type varchar(255),
  payload json,
  eventId UUID,
  since timestamp,
  until timestamp,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS participant (
  id UUID NOT NULL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  hash varchar(255),
  raffleId UUID
);

CREATE TABLE IF NOT EXISTS prizes (
  id UUID NOT NULL PRIMARY KEY,
  name VARCHAR(255),
  description text,
  orderIndex integer,
  raffleId UUID
);

CREATE TABLE IF NOT EXISTS winners (
  id UUID NOT NULL PRIMARY KEY,
  participantId UUID,
  prizeId UUID,
  raffleId UUID,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
  id UUID NOT NULL PRIMARY KEY,
  username varchar(255),
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bartolos (
  id UUID NOT NULL PRIMARY KEY,
  name varchar(255),
  description varchar(255),
  status varchar(255),
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
)
