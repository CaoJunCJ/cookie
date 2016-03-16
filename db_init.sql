DROP DATABASE IF EXISTS `cookiedb`;

CREATE DATABASE `cookiedb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE cookiedb;

CREATE TABLE `cookiedb`.`category` (
  `idcategory_id` INT NOT NULL COMMENT '',
  `name` VARCHAR(255) NOT NULL COMMENT '',
  PRIMARY KEY (`idcategory_id`)  COMMENT '');
  
CREATE TABLE `cookiedb`.`recipe` (
  `recipe_id` INT NOT NULL COMMENT '',
  `name` VARCHAR(255) NULL COMMENT '',
  `rating` FLOAT NULL COMMENT '',
  `description` TEXT NULL COMMENT '',
  `top_img_path` VARCHAR(521) NULL COMMENT '',
  `steps_id` INT NULL COMMENT '',
  `ingredients_id` INT NULL COMMENT '',
  `tip` TEXT NULL COMMENT '',
  `data_from` VARCHAR(255) NULL COMMENT '',
  `categorys_id` INT NULL COMMENT '',
  `author_id` INT NULL COMMENT '',
  PRIMARY KEY (`recipe_id`)  COMMENT '');
  
 CREATE TABLE `cookiedb`.`steps` (
  `steps_id` INT NOT NULL COMMENT '',
  `index` INT NULL COMMENT '',
  `step_context` TEXT NULL COMMENT '',
  `step_img_path` VARCHAR(512) NULL COMMENT '',
  `has_img` TINYINT NULL COMMENT '');

CREATE TABLE `cookiedb`.`ingredients` (
  `ingredients_id` INT NOT NULL COMMENT '',
  `name` VARCHAR(255) NULL COMMENT '',
  `value` VARCHAR(255) NULL COMMENT '',
  `index` INT NULL COMMENT '');

ALTER TABLE `cookiedb`.`recipe` 
CHANGE COLUMN `categorys_id` `categorys_id_arrays` TEXT NULL DEFAULT NULL COMMENT '' ;

CREATE TABLE `cookiedb`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(255) NULL COMMENT '',
  `email` VARCHAR(255) NULL COMMENT '',
  `phone_number` VARCHAR(45) NULL COMMENT '',
  `sex` INT NULL COMMENT '',
  `hashpass` VARCHAR(128) NULL COMMENT '',
  `type` INT NULL COMMENT '',
  `active` TINYINT NULL COMMENT '',
  PRIMARY KEY (`user_id`)  COMMENT '');
  
INSERT INTO `cookiedb`.`user` (`name`, `email`, `phone_number`, `sex`, `hashpass`, `type`, `active`) VALUES ('admin', 'admin@admin.com', '123456', '0', 'e10adc3949ba59abbe56e057f20f883e', '1', '1');

ALTER TABLE `cookiedb`.`recipe` 
DROP COLUMN `ingredients_id`,
DROP COLUMN `steps_id`;

ALTER TABLE `cookiedb`.`ingredients` 
ADD COLUMN `recipe_id` INT NULL COMMENT '' AFTER `index`,
ADD PRIMARY KEY (`ingredients_id`)  COMMENT '';

ALTER TABLE `cookiedb`.`steps` 
ADD COLUMN `recipe_id` INT NULL COMMENT '' AFTER `has_img`,
ADD PRIMARY KEY (`steps_id`)  COMMENT '';

ALTER TABLE `cookiedb`.`steps` 
CHANGE COLUMN `steps_id` `steps_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '' ;

ALTER TABLE `cookiedb`.`ingredients` 
CHANGE COLUMN `ingredients_id` `ingredients_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '' ;

ALTER TABLE `cookiedb`.`category` 
CHANGE COLUMN `idcategory_id` `category_id` INT(11) NOT NULL COMMENT '' ;

