CREATE TABLE IF NOT EXISTS orders (
  id UUID NOT NULL DEFAULT random_uuid(),
  person_name VARCHAR(250) NOT NULL,
  status VARCHAR(250) NOT NULL,
  amount NUMERIC(10,2)
);

CREATE TABLE IF NOT EXISTS coffee (
  id UUID NOT NULL DEFAULT random_uuid(),
  type VARCHAR(250) NOT NULL,
  price NUMERIC(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS order_item(
  id UUID NOT NULL DEFAULT random_uuid(),
  coffee_id UUID NOT NULL,
  order_id UUID NOT NULL,
  quantity INTEGER NOT NULL
);

INSERT INTO coffee (id, type, price)
VALUES('00000000-0000-0000-0000-000000000000', 'AMERICANO', '3.5');

INSERT INTO coffee (id, type, price)
VALUES('11111111-0000-0000-0000-000000000000', 'CAPPUCCINO', '5.5');

INSERT INTO coffee (id, type, price)
VALUES('22222222-0000-0000-0000-000000000000', 'ESPRESSO', '2.5');

INSERT INTO coffee (id, type, price)
VALUES('33333333-0000-0000-0000-000000000000', 'LATTE', '5.0');