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

INSERT INTO `Socyal`.`LOCALITY`
(`ID`, `NAME`, `CITY`, `LATITUDE`, `LONGITUDE`)
VALUES
(4, 'Gachibowli', 'Hyderabad', 17.438324, 78.363378),
(5, 'Madhapur', 'Hyderabad', 17.44557, 78.386152),
(6, 'banjara Hills', 'Hyderabad', 17.416898, 78.438635);

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
  `NAME` VARCHAR(50) NULL,
  `ADDRESS_ID` INT NULL,
  `CONTACT_ID` INT NULL,
  `IMAGE_URL` VARCHAR(255) NULL,
  `RATING` DOUBLE NULL,
  `OPEN_TIME` DOUBLE NULL,
  `CLOSE_TIME` DOUBLE NULL,
  `CHECKINS` INT NULL,
  `CUISINES` VARCHAR(255) NULL,
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

	
	