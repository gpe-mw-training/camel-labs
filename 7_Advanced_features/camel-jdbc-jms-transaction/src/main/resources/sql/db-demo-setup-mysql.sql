-- First create a MySQL Database with schema name = fuse_demo
-- Next run the following script

DROP SCHEMA fuse_demo;

CREATE SCHEMA fuse_demo;

CREATE TABLE `fuse_demo`.`Payments` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `from` VARCHAR(32) NULL ,
  `to` VARCHAR(32) NULL ,
  `amount` DOUBLE NULL ,
  `currency` VARCHAR(32) NULL ,
  PRIMARY KEY (`id`) );

CREATE  TABLE `fuse_demo`.`ProcessedPayments` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `paymentIdentifier` VARCHAR(32) NULL ,
  PRIMARY KEY (`id`) );

