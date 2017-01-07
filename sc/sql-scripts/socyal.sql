CREATE SCHEMA IF NOT EXISTS `Socyal` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `Socyal`.`LOCALITY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(200) NULL,
  `CITY` VARCHAR(100) NULL,
  `LATITUDE` DOUBLE NULL,
  `LONGITUDE` DOUBLE NULL,
  PRIMARY KEY (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `Socyal`.`ADDRESS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ADDRESS` VARCHAR(1000) NULL,
  `LOCALITY_ID` INT NOT NULL,
  `CITY` VARCHAR(100) NULL,
  `STATE` VARCHAR(100) NULL,
  `COUNTRY` VARCHAR(100) NULL,
  `ZIP` VARCHAR(20) NULL,
  `LATITUDE` DOUBLE NULL,
  `LONGITUDE` DOUBLE NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `ADDRESS_LOCALITY_FK`
    FOREIGN KEY (`LOCALITY_ID`)
    REFERENCES `Socyal`.`LOCALITY` (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

  
CREATE TABLE `Socyal`.`CONTACT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `PHONE1` VARCHAR(20) NOT NULL,
  `PHONE2` VARCHAR(20) NULL,
  `EMAIL` VARCHAR(100) NULL,
  PRIMARY KEY (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;
  
CREATE TABLE `Socyal`.`MERCHANT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(50) NOT NULL,
  `ADDRESS_ID` INT NOT NULL,
  `CONTACT_ID` INT NOT NULL,
  `IMAGE_URL` VARCHAR(255) NOT NULL,
  `RATING` DOUBLE NULL,
  `AVERAGE_COST` DOUBLE NOT NULL,
  `CHECKINS` INT DEFAULT 0,
  `CUISINE` VARCHAR(255) NOT NULL,
  `TYPE`  VARCHAR(255) NOT NULL,
  `PROMOTION` INT DEFAULT 0,
  PRIMARY KEY (`ID`),
  INDEX `MERCHANT_ADDRESS_FK_idx` (`ADDRESS_ID` ASC),
  INDEX `MERCHANT_CONTACT_FK_idx` (`CONTACT_ID` ASC),
  CONSTRAINT `MERCHANT_ADDRESS_FK`
    FOREIGN KEY (`ADDRESS_ID`)
    REFERENCES `Socyal`.`ADDRESS` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `MERCHANT_CONTACT_FK`
    FOREIGN KEY (`CONTACT_ID`)
    REFERENCES `Socyal`.`CONTACT` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `Socyal`.`TIMING` (
  `ID` INT NOT NULL,
  `MERCHANT_ID` INT NOT NULL,
  `DAY` VARCHAR(10) NOT NULL,
  `OPEN` INT NOT NULL,
  `CLOSE` INT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `MERCHANT_FK`
    FOREIGN KEY (`MERCHANT_ID`)
    REFERENCES `Socyal`.`MERCHANT` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `Socyal`.`USER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` VARCHAR(100) NOT NULL,
  `LAST_NAME` VARCHAR(100) NULL,
  `IMAGE_URL` VARCHAR(500) NULL,
  `EMAIL` VARCHAR(100) NULL,
  `GENDER` VARCHAR(20) NULL,
  `FACEBOOK_LINK` VARCHAR(500) NULL,
  `FACEBOOK_ID` VARCHAR(100) NULL,
  `FACEBOOK_TOKEN` VARCHAR(1000) NULL,
  `CREATED_DATETIME` TIMESTAMP NULL,
  `UPDATED_DATETIME` TIMESTAMP NULL,
  PRIMARY KEY (`ID`))
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `Socyal`.`MERCHANT_QR_MAPPING` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `QR_CODE` VARCHAR(50) NOT NULL,
  `MERCHANT_ID` INT NOT NULL,
  `CARD_ID` INT NOT NULL,
  `STATUS` BIT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE (`QR_CODE`),
  CONSTRAINT `MERCHANT_QR_FK`
    FOREIGN KEY (`MERCHANT_ID`)
    REFERENCES `Socyal`.`MERCHANT` (`ID`))
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;


CREATE TABLE `Socyal`.`CHECKIN` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USER_ID` INT NOT NULL,
  `MERCHANT_ID` INT NOT NULL,
  `STATUS` VARCHAR(20) NOT NULL,
  `CHECKIN_DATETIME` TIMESTAMP NULL,
  `APPROVED_DATETIME` TIMESTAMP NULL,
  `LIKE_COUNT` INT NULL DEFAULT 0,
  `QR_CODE` VARCHAR(50) NULL,
  `PREVIOUS_CHECKIN_COUNT` INT NULL,
  `REWARD_MESSAGE`  VARCHAR(500) NULL,
  `UPDATED_DATETIME` TIMESTAMP NULL,
  PRIMARY KEY (`ID`),
  INDEX `CHECKIN_MERCHANT_FK_idx` (`MERCHANT_ID` ASC),
   INDEX `CHECKIN_USER_FK_idx` (`USER_ID` ASC),
  CONSTRAINT `CHECKIN_MERCHANT_FK`
    FOREIGN KEY (`MERCHANT_ID`)
    REFERENCES `Socyal`.`MERCHANT` (`ID`),
  CONSTRAINT `CHECKIN_USER_FK`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `Socyal`.`USER` (`ID`))
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

	
CREATE TABLE `Socyal`.`CHECKIN_TAGGED_USER_MAPPING` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CHECKIN_ID` INT NOT NULL,
  `USER_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `TAGGED_USER_CHECKIN_ID_FK`
    FOREIGN KEY (`CHECKIN_ID`)
    REFERENCES `Socyal`.`CHECKIN` (`ID`),
  CONSTRAINT `TAGGED_USER_ID_FK`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `Socyal`.`USER` (`ID`))
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;


DELIMITER $$
CREATE FUNCTION Socyal.DISTANCE(Lat1 double, Lon1 double, Lat2 double, Lon2 double) RETURNS double
DETERMINISTIC
BEGIN
DECLARE RADIUS INT;
DECLARE DEGTORAD DOUBLE;
SET RADIUS = 3963;
SET DEGTORAD = 57.29577951;
  RETURN(IFNULL(RADIUS,0) * ACOS((sin(IFNULL(Lat1, 0) / DEGTORAD) * SIN(IFNULL(Lat2, 0) / DEGTORAD)) +
        (COS(IFNULL(Lat1, 0) / DEGTORAD) * COS(IFNULL(Lat2, 0) / DEGTORAD) *
         COS(IFNULL(Lon2, 0) / DEGTORAD - IFNULL(Lon1, 0)/ DEGTORAD))));
END;