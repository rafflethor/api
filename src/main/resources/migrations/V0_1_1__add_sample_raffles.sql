INSERT INTO users
  (id, username, password)
VALUES
  ('e8bceac0-905c-11e8-9eb6-529269fb1459',
   'organizer1@email.com',
   '3ee1ef7de89c0e5117bc9e40984f1f0ac23819c830cc7a2ee8f8597effdf7cad');

INSERT INTO users
  (id, username, password)
VALUES
  ('e8bced86-905c-11e8-9eb6-529269fb1459',
   'organizer2@email.com',
   '89379660c99d2939fafb6b708cd461f80a0eca8d025aa991a96d5559d8247d6e');

INSERT INTO organizations
  (id, name, description, createdBy)
VALUES
  ('135ef3e0-7c49-11e8-adc0-fa7ae01bbebc',
   'The SuperUnconference',
   'This is a ficticious conference where everything could happen',
   'e8bceac0-905c-11e8-9eb6-529269fb1459');

INSERT INTO raffles
  (id, name, type, status, noWinners, since, until, payload, organizationId, createdBy)
VALUES
  ('cc00c00e-6a42-11e8-adc0-fa7ae01bbebc',
   'SuperUnconference T-Shirt',
   'LIVE',
   'NEW',
   1,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#groovylang"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc',
   'e8bceac0-905c-11e8-9eb6-529269fb1459');

INSERT INTO raffles
  (id, name, type, status, noWinners, since, until, payload, organizationId, createdBy)
VALUES
  ('cc00c2ac-6a42-11e8-adc0-fa7ae01bbebc',
   'SuperUnconference Free Ticket',
   'TWITTER',
   'NEW',
   2,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#piweek14"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc',
   'e8bceac0-905c-11e8-9eb6-529269fb1459');

INSERT INTO raffle_spot
    (id, raffleId)
VALUES
    ('AAAA', 'cc00c2ac-6a42-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffle_spot
    (id, raffleId)
VALUES
    ('BBBB', 'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc');
