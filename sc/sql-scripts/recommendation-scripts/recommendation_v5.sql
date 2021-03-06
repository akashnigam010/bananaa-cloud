-- add column DISH.VEGNONVEG
ALTER TABLE bna.`DISH`
ADD COLUMN `VEGNONVEG` INT NOT NULL DEFAULT 0 AFTER `NAME_ID`

-- add column MERCHANT.CAN_EDIT
ALTER TABLE bna.`MERCHANT`
ADD COLUMN `CAN_EDIT` BIT NULL DEFAULT 0 AFTER `IS_ACTIVE`

-- create table bna.vegnonveg
CREATE TABLE `bna`.`VEGNONVEG` (
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

INSERT INTO `bna`.`VEGNONVEG` (`NAME`, `NAME_ID`) VALUES ('Vegetarian', 'veg');
INSERT INTO `bna`.`VEGNONVEG` (`NAME`, `NAME_ID`) VALUES ('Non Vegetarian', 'nonveg');
INSERT INTO `bna`.`VEGNONVEG` (`NAME`, `NAME_ID`) VALUES ('Both', 'both');

-- create table bna.USER_SUGGESTION_PREF_MAPPING
CREATE TABLE `bna`.`USER_SUGGESTION_PREF_MAPPING` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USER_ID` INT NOT NULL,
  `SUGGESTION_ID` INT NOT NULL,
	PRIMARY KEY (`ID`),
	CONSTRAINT `USPM_USER_FK`
	FOREIGN KEY (`USER_ID`)
	REFERENCES `bna`.`USER` (`ID`),
	CONSTRAINT `USPM_SUGGESTION_FK`
	FOREIGN KEY (`SUGGESTION_ID`)
	REFERENCES `bna`.`SUGGESTION` (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

-- create table bna.USER_CUISINE_PREF_MAPPING
CREATE TABLE `bna`.`USER_CUISINE_PREF_MAPPING` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USER_ID` INT NOT NULL,
  `CUISINE_ID` INT NOT NULL,
	PRIMARY KEY (`ID`),
	CONSTRAINT `UCPM_USER_FK`
	FOREIGN KEY (`USER_ID`)
	REFERENCES `bna`.`USER` (`ID`),
	CONSTRAINT `UCPM_CUISINE_FK`
	FOREIGN KEY (`CUISINE_ID`)
	REFERENCES `bna`.`CUISINE` (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

-- add column USER.VEGNONVEG_ID
ALTER TABLE bna.`USER`
ADD COLUMN `VEGNONVEG_ID` INT AFTER `NAME_ID`,
ADD CONSTRAINT `USER_VEGNONVEG_FK`
    FOREIGN KEY (`VEGNONVEG_ID`)
    REFERENCES `bna`.`VEGNONVEG` (`ID`);
    
-- create table bna.USER_STATUS
CREATE TABLE `bna`.`USER_STATUS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `STATUS` VARCHAR(200) NULL,
  `CREATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

-- add column USER.USER_STATUS_ID
ALTER TABLE bna.`USER`
ADD COLUMN `USER_STATUS_ID` INT AFTER `NAME_ID`,
ADD CONSTRAINT `USER_STATUS_FK`
    FOREIGN KEY (`USER_STATUS_ID`)
    REFERENCES `bna`.`USER_STATUS` (`ID`);