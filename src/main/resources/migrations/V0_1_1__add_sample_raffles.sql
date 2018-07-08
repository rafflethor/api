INSERT INTO events
  (id, name, description)
VALUES
  ('135ef3e0-7c49-11e8-adc0-fa7ae01bbebc',
   'The SuperUnconference',
   'This is a ficticious conference where everything could happen');

INSERT INTO raffles
  (id, name, type, noWinners, since, until, payload, eventId)
VALUES
  ('cc00c00e-6a42-11e8-adc0-fa7ae01bbebc',
   'raffle1',
   'TWITTER',
   1,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#groovylang"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffles
  (id, name, type, noWinners, since, until, payload, eventId)
VALUES
  ('cc00c2ac-6a42-11e8-adc0-fa7ae01bbebc',
   'raffle2',
   'TWITTER',
   2,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#ratpackweb"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffles
  (id, name, type, noWinners, since, until, payload, eventId)
VALUES
  ('cc00c3ec-6a42-11e8-adc0-fa7ae01bbebc',
   'raffle3',
   'TWITTER',
   3,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#graphql"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffles
  (id, name, type, noWinners, since, until, payload, eventId)
VALUES
  ('cc00c50e-6a42-11e8-adc0-fa7ae01bbebc',
   'raffle4',
   'TWITTER',
   4,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#gql"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffles
  (id, name, type, noWinners, since, until, payload, eventId)
VALUES
  ('cc00c630-6a42-11e8-adc0-fa7ae01bbebc',
   'raffle5',
   'TWITTER',
   5,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#science"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');

INSERT INTO raffles
  (id, name, type, noWinners, since, until, payload, eventId)
VALUES
  ('cc00cdf6-6a42-11e8-adc0-fa7ae01bbebc',
   'raffle6',
   'TWITTER',
   6,
   '2018/05/31',
   '2018/06/06',
   '{"hashtag": "#community"}',
   '135ef3e0-7c49-11e8-adc0-fa7ae01bbebc');
