CREATE TABLE IF NOT EXISTS users (
  id UUID NOT NULL PRIMARY KEY,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS organizations (
  id UUID NOT NULL PRIMARY KEY,
  name VARCHAR(255),
  description text,
  createdBy UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS raffles (
  id UUID NOT NULL PRIMARY KEY,
  name VARCHAR(255),
  noWinners INT,
  type varchar(255),
  payload json,
  organizationId UUID NOT NULL REFERENCES organizations (id) ON DELETE CASCADE,
  status varchar(50) NOT NULL DEFAULT 'NEW',
  since timestamp,
  until timestamp,
  createdBy UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS raffle_spot (
  id varchar(4) NOT NULL,
  raffleId UUID NOT NULL REFERENCES raffles (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS participants (
  id UUID NOT NULL PRIMARY KEY,
  social VARCHAR(255),
  nick VARCHAR(255),
  hash varchar(255) NOT NULL,
  raffleId UUID NOT NULL REFERENCES raffles (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS winners (
  id UUID NOT NULL PRIMARY KEY,
  participantId UUID NOT NULL REFERENCES participants (id),
  raffleId UUID NOT NULL REFERENCES raffles (id) ON DELETE CASCADE,
  ordering integer,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bartolos (
  id UUID NOT NULL PRIMARY KEY,
  name varchar(255),
  description varchar(255),
  status varchar(255),
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
)
