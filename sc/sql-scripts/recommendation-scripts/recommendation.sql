CREATE SCHEMA IF NOT EXISTS `bna` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `bna`.`LOCALITY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(50) NULL,
  `CITY` VARCHAR(50) NULL,
  `LATITUDE` DOUBLE NULL,
  `LONGITUDE` DOUBLE NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`ADDRESS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ADDRESS` VARCHAR(150) NULL,
  `LOCALITY_ID` INT NOT NULL,
  `LATITUDE` DOUBLE NULL,
  `LONGITUDE` DOUBLE NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  CONSTRAINT `ADDRESS_LOCALITY_FK`
    FOREIGN KEY (`LOCALITY_ID`)
    REFERENCES `bna`.`LOCALITY` (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

  
CREATE TABLE `bna`.`CONTACT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `PHONE1` VARCHAR(15) NOT NULL,
  `PHONE2` VARCHAR(15) NULL,
  `EMAIL` VARCHAR(40) NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;
  
CREATE TABLE `bna`.`MERCHANT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(40) NOT NULL,
  `NAME_ID` VARCHAR(50) NOT NULL,
  `ADDRESS_ID` INT NOT NULL,
  `CONTACT_ID` INT NOT NULL,
  `IMAGE_URL` VARCHAR(100) NOT NULL,
  `THUMBNAIL` VARCHAR(100) NOT NULL,
  `AVERAGE_COST` DOUBLE NOT NULL,
  `TYPE`  VARCHAR(50) NOT NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `IS_ACTIVE` BIT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  UNIQUE(`NAME_ID`),
  INDEX `MERCHANT_ADDRESS_FK_idx` (`ADDRESS_ID` ASC),
  INDEX `MERCHANT_CONTACT_FK_idx` (`CONTACT_ID` ASC),
  CONSTRAINT `MERCHANT_ADDRESS_FK`
    FOREIGN KEY (`ADDRESS_ID`)
    REFERENCES `bna`.`ADDRESS` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `MERCHANT_CONTACT_FK`
    FOREIGN KEY (`CONTACT_ID`)
    REFERENCES `bna`.`CONTACT` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`TIMING` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `MERCHANT_ID` INT NOT NULL,
  `DAY` VARCHAR(10) NOT NULL,
  `OPEN` VARCHAR(4) NOT NULL,
  `CLOSE` VARCHAR(4) NOT NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  CONSTRAINT `MERCHANT_FK`
    FOREIGN KEY (`MERCHANT_ID`)
    REFERENCES `bna`.`MERCHANT` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`SUGGESTION` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(30) NOT NULL,
  `NAME_ID` VARCHAR(40) NOT NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE (`NAME_ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`CUISINE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(30) NULL,
  `NAME_ID` VARCHAR(40) NOT NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE (`NAME_ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`DISH` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(50) NOT NULL,
  `NAME_ID` VARCHAR(60) NOT NULL,
  `SUGGESTION_ID` INT NULL,
  `CUISINE_ID` INT NULL,
  `MERCHANT_ID` INT NOT NULL,
  `IMAGE_URL` VARCHAR(100) NOT NULL,
  `THUMBNAIL` VARCHAR(100) NOT NULL,
  `IS_ACTIVE` BIT NULL DEFAULT 1,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  CONSTRAINT UC_MERCHANT_DISH_NAME_ID UNIQUE (`MERCHANT_ID`,`NAME_ID`),
  CONSTRAINT `DISH_MERCHANT_FK`
    FOREIGN KEY (`MERCHANT_ID`)
    REFERENCES `bna`.`MERCHANT` (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`USER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UID` VARCHAR(100) NOT NULL,
  `FIRST_NAME` VARCHAR(50) NOT NULL,
  `LAST_NAME` VARCHAR(30) NULL,
  `NAME_ID` VARCHAR(100) NOT NULL,
  `IMAGE_URL` VARCHAR(100) NULL,
  `EMAIL` VARCHAR(40) NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE (`NAME_ID`))
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`RECOMMENDATION` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `DISH_ID` INT NOT NULL,
  `USER_ID` INT NOT NULL,
  `IS_ACTIVE` BIT NULL DEFAULT 1,
  `DESCRIPTION` VARCHAR(300) NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`ID`),
	CONSTRAINT `RCMD_DISH_FK`
	FOREIGN KEY (`DISH_ID`)
	REFERENCES `bna`.`DISH` (`ID`),
	CONSTRAINT `RCMD_USER_FK`
	FOREIGN KEY (`USER_ID`)
	REFERENCES `bna`.`USER` (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`ITEM_IMAGE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(50) NOT NULL,
  `IMAGE_URL` VARCHAR(100) NOT NULL,
  `THUMBNAIL` VARCHAR(100) NOT NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE (`NAME`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;