-- Address
INSERT INTO `Socyal`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('Plot 15/1, 1st Floor, Sector 2, Opposite Cyber Gateway, Hitech City', 1, 'Hyderabad', 'Telangana', 'India', '500081', '17.446297', '78.377693');

-- Contact
INSERT INTO `Socyal`.`CONTACT` (`PHONE1`) VALUES ('04023116606');

-- ******************************** BREAK SCRIPT ******************************************************
-- *********** GET AUTO GENERATED ADDRESS_ID and CONTACT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ******************************************************


-- Merchant

INSERT INTO `Socyal`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('whiteboard-cafe-hitech-city', 'Whiteboard Cafe', 7, 7, 'https://s3.ap-south-1.amazonaws.com/bna-s3/hyderabad/whiteboard-cafe-hitech-city.png', 3.5, 0, 'Cafe', 'Cafe', 600.00);


-- ******************************** BREAK SCRIPT ****************************************
-- *********** GET AUTO GENERATED MERCHANT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ****************************************


-- Timing

INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (7, 'MONDAY', '0800', '2230');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (7, 'TUESDAY', '0800', '2230');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (7, 'WEDNESDAY', '0800', '2230');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (7, 'THURSDAY', '0800', '2230');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (7, 'FRIDAY', '0800', '2230');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (7, 'SATURDAY', '0800', '2230');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (7, 'SUNDAY', '0800', '2230');

-- Merchant_QR_Mapping

-- Rewards

-- Merchant_Login



