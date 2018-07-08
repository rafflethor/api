CREATE TABLE IF NOT EXISTS events (
  id UUID,
  name VARCHAR(255),
  description text,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS raffles (
  id UUID,
  name VARCHAR(255),
  noWinners INT,
  type varchar(255),
  payload json,
  eventId UUID,
  since timestamp,
  until timestamp,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS winners (
  id UUID,
  name VARCHAR(255),
  raffleId UUID,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
  id UUID,
  username varchar(255),
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bartolos (
  id UUID,
  name varchar(255),
  description varchar(255),
  status varchar(255),
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
)
