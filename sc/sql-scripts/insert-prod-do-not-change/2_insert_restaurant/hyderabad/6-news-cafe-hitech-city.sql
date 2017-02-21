-- Address
INSERT INTO `Socyal`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('2nd & 3rd Floor, Inorbit Mall, Hitech City', 1, 'Hyderabad', 'Telangana', 'India', '500081', '17.434819', '78.387108');

-- Contact
INSERT INTO `Socyal`.`CONTACT` (`PHONE1`, `PHONE2`) VALUES ('9502311155', '9502311133');

-- ******************************** BREAK SCRIPT ******************************************************
-- *********** GET AUTO GENERATED ADDRESS_ID and CONTACT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ******************************************************


-- Merchant

INSERT INTO `Socyal`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('news-cafe-hitech-city', 'News Cafe', 6, 6, 'https://s3.ap-south-1.amazonaws.com/bna-s3/hyderabad/news-cafe-hitech-city.png', 3.5, 0, 'Continental, Italian, American', 'Pub, Casual Dining', 1800.00);


-- ******************************** BREAK SCRIPT ****************************************
-- *********** GET AUTO GENERATED MERCHANT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ****************************************


-- Timing

INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (6, 'MONDAY', '1130', '2330');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (6, 'TUESDAY', '1130', '2330');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (6, 'WEDNESDAY', '1130', '2330');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (6, 'THURSDAY', '1130', '2330');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (6, 'FRIDAY', '1130', '2330');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (6, 'SATURDAY', '1130', '2330');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (6, 'SUNDAY', '1130', '2330');

-- Merchant_QR_Mapping

INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-01', 6, '1', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-02', 6, '2', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-03', 6, '3', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-04', 6, '4', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-05', 6, '5', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-06', 6, '6', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-07', 6, '7', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-08', 6, '8', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-09', 6, '9', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-10', 6, '10', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-11', 6, '11', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-12', 6, '12', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-13', 6, '13', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-14', 6, '14', TRUE);
INSERT INTO `Socyal`.`MERCHANT_QR_MAPPING` (`QR_CODE`, `MERCHANT_ID`, `CARD_ID`, `STATUS`) VALUES ('1-6-15', 6, '15', TRUE);

-- Rewards
INSERT INTO `Socyal`.`REWARDS` (`MERCHANT_ID`, `REWARD`) VALUES (6, 'Mug of Beer');
INSERT INTO `Socyal`.`REWARDS` (`MERCHANT_ID`, `REWARD`) VALUES (6, 'Welcome Drink');
INSERT INTO `Socyal`.`REWARDS` (`MERCHANT_ID`, `REWARD`) VALUES (6, 'Mocktail');
-- Merchant_Login



