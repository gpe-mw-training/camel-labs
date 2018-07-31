This Exercises shows how to use JDBC - JMS Transaction to make sure that either all database statements and
JMS messages are read and written or none of them meaning reads are pushed back onto the destination and writes
are undone.

Setup:
======

Before we can go ahead and run the exercises we need to install PostgreSQL and configure the demo database.
The installation depends heavily on the OS and for more info please check out the notes at the bottom of the
page. After that we need to create the DB User, Database and Tables for our little example. 
PostgreSQL ships with a handy command-line tool called 'psql'. 
You can open the PostgreSQL shell from a DOS prompt. Go to: 
<PostgreSQL install Dir>\8.4\bin
Type: 
    psql -U postgres
This will start the command line tool as 'postgres' user.
From the command line tool, you can execute the exercise setup script like this:

    \i <Path to this exercise>/src/main/resources/sql/db-demo-setup.sql

After that please check that you are connected to the right DB. You can force that with this:

    \connect fuse_demo;

Now make sure that the tables are created:

    \dt

You should see this:

fuse_demo=# \dt
               List of relations
 Schema |       Name        | Type  |   Owner
--------+-------------------+-------+-----------
 public | Payments          | table | fuse_user
 public | ProcessedPayments | table | fuse_user
(2 rows)

Congratulation, you have now successfully completed the DB setup.

Here are several commands that you may need to run periodically from the 'psql' shell.
validating the solution.
To show the contents of the "Payments" table:

SELECT * FROM "Payments";

To show the contents of the "ProcessedPayments" table:

SELECT * FROM "ProcessedPayments";

To delete all rows from "Payments" table:

DELETE FROM "Payments";

To delete all rows from "ProcessedPayments" table:

DELETE FROM "ProcessedPayments";

+++++++++++++++++++++++++++++++++++++

MySQL Setup
========

1) Install MySQL Database - http://dev.mysql.com/downloads/
2) Install MySQL Workbench - http://dev.mysql.com/downloads/workbench/
3) Start server using command 'udo mysqld_safe &'
4) Open Workbench
5) Open SQL Tab to execute the content of the following query
   src/main/resources/sql/db-demo-setup-mysql.sql


Deployment:
===========
1) Running route from command-line. 

a) Start the ActiveMQ broker using (Note: you can skip this step if JBoss Fuse is running locally because JBoss Fuse
has an embedded broker that can be used)

    mvn -P amq-run

b) In another command line console start Camel using:

    mvn -P camel-postgresql-run (PostgreSQL Database) OR mvn -P camel-mysql-run (to use MySQL Database) or mvn -P camel-h2-run (to use H2 Database)

You will find the output of the routes inside:

    ./target/test-classes/camel/out

and you can add new Payment XML files into:

    ./target/test-classes/camel/in

2) Build, Install and Deploy it as OSGi bundle:

    mvn clean install

then please enter this in the Karaf Console:
	features:install camel-core
    features:install camel-jms
    features:install camel-jaxb
    osgi:install mvn:org.springframework/spring-jdbc/2.5.5
    osgi:install wrap:mvn:postgresql/postgresql/9.1-901-1.jdbc4
    osgi:install wrap:mvn:com.h2database/h2/1.4.182
    osgi:install -s mvn:com.redhat.gpte.training/camel-exercise-jdbc-jms-transaction/1.0

(Alternatively you can install the feature using 'features:install camel-exercise-jdbc-jms-transaction' see step 3)

ATTENTION: in case of errors and a rebuild inside maven (mvn install) you need to update the project using
           the bundle number given by the original install because a subsequent install won't do anything good:

    osgi:update <bundle number>

also make sure with osgi:list that the application is started and if not then

    osgi:start <bundle number>

3) Alternative to 2) Install it as feature (need to add the features URL ahead of time, please see the camel-exercise-features project)

    features:install camel-exercise-jdbc-jms-transaction

In order to make the route do something you can copy an XML Payment file into '/camel-exercise/jdbc-jms-transactions/in'
directory:

    cp src/test/resources/camel/in/VariousUSPayments.xml /camel-exercise/jdbc-jms-transactions/in/

ATTENTION:
    You need to check the DB in order to see what records were written to the Payments table (remember that the
    route will write to the DB anyhow even if it fails later). You can then take a look at the output directory to
    see what data were written to the JMS Queue and eventually to the file.

PostgreSQL Installation
=======================

Windows: An installer can be found here: http://www.postgresql.org/download/windows

Through the same link an installer can be found for Linux and Mac provided by EnterpriseDB. The only drawback is
that one needs to register there.

Note:
    Installing on Mac OS X can be a problem with the 64-bit kernel. You need to reboot the Mac into 32-bit by holing
    down the 3 and 2 key. After the installation one can boot into 64-bit again with any issues.