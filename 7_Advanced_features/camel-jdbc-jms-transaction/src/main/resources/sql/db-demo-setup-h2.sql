-- This Script only works if executed in a H2 shell otherwise create the user and Database as
-- mentioned below, connect to them and open the SQL Editor to execute the Create Table statements

-- Connect to H2 using this connection : jdbc:h2:tcp://localhost:9123/jbossfuse-demo

DROP TABLE "Payments";

CREATE TABLE "Payments"
(
  "from" character varying(32),
  "to" character varying(32),
  "amount" double precision,
  "currency" character varying(32),
  id serial NOT NULL,
  CONSTRAINT primke PRIMARY KEY (id)
);

DROP TABLE "ProcessedPayments";

CREATE TABLE "ProcessedPayments"
(
  "paymentIdentifier" character varying(32),
  id serial NOT NULL,
  CONSTRAINT processedpaymentspk PRIMARY KEY (id)
);
