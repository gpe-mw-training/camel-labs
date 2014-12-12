-- This Script only works if executed in a PostgreSQL shell otherwise create the user and Database as
-- mentioned below, connect to them and open the SQL Editor to execute the Create Table statements

CREATE USER fuse_user CREATEDB PASSWORD 'fuse_user';

CREATE DATABASE fuse_demo WITH OWNER = fuse_user;

\connect fuse_demo;

-- The rest of that script can be executed in the SQL Editor with the 'fuse_demo' database selected or in the shell
-- if there is a need to recreate the DB

DROP TABLE "Payments";

CREATE TABLE "Payments"
(
  "from" character varying(32),
  "to" character varying(32),
  "amount" double precision,
  "currency" character varying(32),
  id serial NOT NULL,
  CONSTRAINT primke PRIMARY KEY (id)
)
WITH (OIDS=FALSE);
ALTER TABLE "Payments" OWNER TO fuse_user;

DROP TABLE "ProcessedPayments";

CREATE TABLE "ProcessedPayments"
(
  "paymentIdentifier" character varying(32),
  id serial NOT NULL,
  CONSTRAINT processedpaymentspk PRIMARY KEY (id)
)
WITH (OIDS=FALSE);
ALTER TABLE "ProcessedPayments" OWNER TO fuse_user;
