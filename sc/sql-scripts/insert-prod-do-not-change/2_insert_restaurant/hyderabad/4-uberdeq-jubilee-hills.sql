-- Address
INSERT INTO `Socyal`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('8-3-293/82/A/70, 4th Floor, Anshu Colours, Near TV5 Office, Jubilee Hills', 2, 'Hyderabad', 'Telangana', 'India', '500033', '17.428799', '78.417945');

-- Contact
INSERT INTO `Socyal`.`CONTACT` (`PHONE1`, `PHONE2`) VALUES ('9000774423', '9000774425');

-- ******************************** BREAK SCRIPT ******************************************************
-- *********** GET AUTO GENERATED ADDRESS_ID and CONTACT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ******************************************************

-- Merchant

INSERT INTO `Socyal`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('uberdeq-jubilee-hills', 'Uberdeq', 4, 4, 'https://s3.ap-south-1.amazonaws.com/bna-s3/hyderabad/uberdeq-jubilee-hills.png', '3.5', '0', 'Finger Food', 'Pub', 2000.00);

-- ******************************** BREAK SCRIPT ****************************************
-- *********** GET AUTO GENERATED MERCHANT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ****************************************


-- Timing

INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'MONDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'TUESDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'WEDNESDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'THURSDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'FRIDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'SATURDAY', '1200', '2400');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'SUNDAY', '1230', '2400');

-- Merchant_QR_Mapping

-- Rewards

-- Merchant_Login