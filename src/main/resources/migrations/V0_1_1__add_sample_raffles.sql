INSERT INTO organizations
  (id, name, description)
VALUES
  ('135ef3e0-7c49-11e8-adc0-fa7ae01bbebc',
   'The SuperUnconference',
   'This is a ficticious conference where everything could happen');

INSERT INTO raffles
  (id, name, type, status, noWinners, since, until, payload, organizationId)
VALUES
  ('cc00c00e-6a42-11e8-adc0-fa7ae01bbebc',
   'SuperUnconference T-Shirt',
   'LIVE',
   'NEW',
   1,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#groovylang"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffles
  (id, name, type, status, noWinners, since, until, payload, organizationId)
VALUES
  ('cc00c2ac-6a42-11e8-adc0-fa7ae01bbebc',
   'SuperUnconference Free Ticket',
   'TWITTER',
   'NEW',
   2,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#piweek14"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffle_spot
    (id, raffleId)
VALUES
    ('AAAA', 'cc00c2ac-6a42-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffle_spot
    (id, raffleId)
VALUES
    ('BBBB', 'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc');
