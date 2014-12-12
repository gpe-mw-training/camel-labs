create table projects (
  id integer primary key,
  project varchar(50),
  license varchar(5)
);
insert into projects values (1, 'Apache Camel', 'ASF');
insert into projects values (2, 'Apache ActiveMQ', 'ASF');
insert into projects values (3, 'Apache Karaf', 'ASF');
insert into projects values (4, 'JBoss Fabric8', 'ASF');