-- Address
INSERT INTO `Socyal`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('5th Floor, Inorbit Mall, Hitech City', 1, 'Hyderabad', 'Telangana', 'India', '500081', '17.434331', '78.386764');

-- Contact
INSERT INTO `Socyal`.`CONTACT` (`PHONE1`) VALUES ('7731019977');

-- ******************************** BREAK SCRIPT ******************************************************
-- *********** GET AUTO GENERATED ADDRESS_ID and CONTACT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ******************************************************


-- Merchant

INSERT INTO `Socyal`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('fusion-9-hitech-city', 'Fusion 9', '1', '1', 'https://s3.ap-south-1.amazonaws.com/bna-s3/hyderabad/fusion-9-hitech-city.png', '3.5', '0', 'Continental, Italian, North Indian, Asian', 'Fine Dining', 1600.00);


-- ******************************** BREAK SCRIPT ****************************************
-- *********** GET AUTO GENERATED MERCHANT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ****************************************


-- Timing

INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'MONDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'TUESDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'WEDNESDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'THURSDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'FRIDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'SATURDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'SUNDAY', '1200', '2400');

-- Merchant_QR_Mapping

INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-01', 1, '1', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-02', 1, '2', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-03', 1, '3', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-04', 1, '4', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-05', 1, '5', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-06', 1, '6', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-07', 1, '7', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-08', 1, '8', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-09', 1, '9', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-10', 1, '10', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-11', 1, '11', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-12', 1, '12', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-13', 1, '13', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-14', 1, '14', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('12346-15', 1, '15', TRUE);

-- Rewards

INSERT INTO `Socyal`.`REWARDS` (`MERCHANT_ID`, `REWARD`) VALUES (1, 'Welcome drink');
INSERT INTO `Socyal`.`REWARDS` (`MERCHANT_ID`, `REWARD`) VALUES (1, 'Cocktail');
INSERT INTO `Socyal`.`REWARDS` (`MERCHANT_ID`, `REWARD`) VALUES (1, 'Full course veg buffet');
INSERT INTO `Socyal`.`REWARDS` (`MERCHANT_ID`, `REWARD`) VALUES (1, 'Full course non veg buffet');


-- Merchant_Login
INSERT INTO `Socyal`.`MERCHANT_LOGIN` (`MERCHANT_ID`, `USERNAME`, `PASSWORD`, `DEVICE_ID`) VALUES (1, 'fusion9', 'Fusion91@3', 1);


