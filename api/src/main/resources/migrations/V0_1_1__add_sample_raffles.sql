INSERT INTO users
  (id, username, password, roles)
VALUES
  ('e8bceac0-905c-11e8-9eb6-529269fb1459',
   'organizer1@email.com',
   '3ee1ef7de89c0e5117bc9e40984f1f0ac23819c830cc7a2ee8f8597effdf7cad',
   '{"USER"}');

INSERT INTO users
  (id, username, password, roles)
VALUES
  ('e8bced86-905c-11e8-9eb6-529269fb1459',
   'organizer2@email.com',
   '89379660c99d2939fafb6b708cd461f80a0eca8d025aa991a96d5559d8247d6e',
   '{"USER"}');

INSERT INTO organizations
  (id, name, description, createdBy)
VALUES
  ('135ef3e0-7c49-11e8-adc0-fa7ae01bbebc',
   'The SuperUnconference',
   'This is a ficticious conference where everything could happen',
   'e8bceac0-905c-11e8-9eb6-529269fb1459');

INSERT INTO raffles
  (id, name, type, status, preventPreviousWinners, noWinners, since, until, payload, organizationId, createdBy)
VALUES
  ('cc00c00e-6a42-11e8-adc0-fa7ae01bbebc',
   'SuperUnconference T-Shirt',
   'LIVE',
   'NEW',
   FALSE,
   1,
   '2018/05/31',
   '2018/06/06',
   '{"code": "madridgug_groovy3"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc',
   'e8bceac0-905c-11e8-9eb6-529269fb1459');

INSERT INTO raffles
  (id, name, type, status, preventPreviousWinners, noWinners, since, until, payload, organizationId, createdBy)
VALUES
  ('cc00c2ac-6a42-11e8-adc0-fa7ae01bbebc',
   'SuperUnconference Free Ticket',
   'TWITTER',
   'NEW',
   TRUE,
   2,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#piweek14"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc',
   'e8bceac0-905c-11e8-9eb6-529269fb1459');

INSERT INTO participants
  (id, social, hash, nick, raffleId)
VALUES
  ('14d66df8-9520-11e8-9eb6-529269fb1459', 'somebody@heremail.com', '20909jffsd', 'John Doe', 'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc');

INSERT INTO participants
  (id, social, hash, nick, raffleId)
VALUES
  ('14d67096-9520-11e8-9eb6-529269fb1459', 'somebodyelse@heremail.com', '20909jffsd', 'John Doe', 'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc');

INSERT INTO winners
  (id, participantId, raffleId, ordering)
VALUES
  ('14d671d6-9520-11e8-9eb6-529269fb1459', '14d66df8-9520-11e8-9eb6-529269fb1459', 'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc', 1);
