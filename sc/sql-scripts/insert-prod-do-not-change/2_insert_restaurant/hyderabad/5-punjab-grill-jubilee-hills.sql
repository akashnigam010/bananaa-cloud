-- Address
INSERT INTO `Socyal`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('4th Floor, Road # 1, Jubilee Check Post Jubilee Hills, Jubilee Hills', 2, 'Hyderabad', 'Telangana', 'India', '500033', '17.425867', '78.414478');

-- Contact
INSERT INTO `Socyal`.`CONTACT` (`PHONE1`, `PHONE2`) VALUES ('04023237777', '8008333555');

-- ******************************** BREAK SCRIPT ******************************************************
-- *********** GET AUTO GENERATED ADDRESS_ID and CONTACT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ******************************************************

-- Merchant

INSERT INTO `Socyal`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('punjab-grill-jubilee-hills', 'Punjab Grill', 5, 5, 'https://s3.ap-south-1.amazonaws.com/bna-s3/hyderabad/punjab-grill-jubilee-hills.png', '3.5', '0', 'North Indian, Mughlai', 'Casual Dining', 1500.00);

-- ******************************** BREAK SCRIPT ****************************************
-- *********** GET AUTO GENERATED MERCHANT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ****************************************


-- Timing

INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'MONDAY', '1200', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'TUESDAY', '1200', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'WEDNESDAY', '1200', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'THURSDAY', '1200', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'FRIDAY', '1200', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'SATURDAY', '1200', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'SUNDAY', '1200', '1530');

INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'MONDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'TUESDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'WEDNESDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'THURSDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'FRIDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'SATURDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (5, 'SUNDAY', '1900', '2300');

-- Merchant_QR_Mapping

-- Rewards

-- Merchant_Login