INSERT INTO organizations
  (id, name, description)
VALUES
  ('135ef3e0-7c49-11e8-adc0-fa7ae01bbebc',
   'The SuperUnconference',
   'This is a ficticious conference where everything could happen');

INSERT INTO raffles
  (id, name, type, noWinners, since, until, payload, organizationId)
VALUES
  ('cc00c00e-6a42-11e8-adc0-fa7ae01bbebc',
   'raffle1',
   'LIVE',
   1,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#groovylang"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');

INSERT INTO prizes
    (id, name, description, raffleId, orderIndex)
VALUES
    ('c68ca88a-88d9-11e8-9a94-a6cf71072f73',
     'Donuts',
     'A donuts',
     'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc',
     0);

INSERT INTO prizes
    (id, name, description, raffleId, orderIndex)
VALUES
    ('c68caad8-88d9-11e8-9a94-a6cf71072f73',
     'Donuts',
     'A bag of candies',
     'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc',
     1);

INSERT INTO raffles
  (id, name, type, noWinners, since, until, payload, organizationId)
VALUES
  ('cc00c2ac-6a42-11e8-adc0-fa7ae01bbebc',
   'raffle2',
   'TWITTER',
   2,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#piweek14"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');

INSERT INTO prizes
    (id, name, description, raffleId, orderIndex)
VALUES
    ('c68ca48e-88d9-11e8-9a94-a6cf71072f73',
     'T-Shirt',
     'T-Shirt of the Piweek',
     'cc00c2ac-6a42-11e8-adc0-fa7ae01bbebc',
     0);

INSERT INTO raffle_spot
    (id, raffleId)
VALUES
    ('AAAA', 'cc00c2ac-6a42-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffle_spot
    (id, raffleId)
VALUES
    ('BBBB', 'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc');


INSERT INTO raffle_spot
    (id)
VALUES
    ('CCCC');

INSERT INTO raffle_spot
    (id)
VALUES
    ('DDDD');
