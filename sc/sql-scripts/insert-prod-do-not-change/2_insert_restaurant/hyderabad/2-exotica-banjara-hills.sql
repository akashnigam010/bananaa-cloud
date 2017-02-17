-- Address
INSERT INTO `Socyal`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('Opposite Audi Showroom, 5th Floor, 12th Square Building, Road 12, Banjara Hills', 3, 'Hyderabad', 'Telangana', 'India', '500034', '17.410573', '78.437276');


-- Contact
INSERT INTO `Socyal`.`CONTACT` (`PHONE1`, `PHONE2`) VALUES ('9652115500', '9550405500');

-- ******************************** BREAK SCRIPT ******************************************************
-- *********** GET AUTO GENERATED ADDRESS_ID and CONTACT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ******************************************************


-- Merchant

INSERT INTO `Socyal`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('exotica-banjara-hills', 'Exotica', '2', '2', 'https://s3.ap-south-1.amazonaws.com/bna-s3/hyderabad/exotica-banjara-hills.png', '3.5', '0', 'Mughlai, North Indian, Chinese', 'Casual Dining, Bar', 1500.00);


-- ******************************** BREAK SCRIPT ****************************************
-- *********** GET AUTO GENERATED MERCHANT_ID AND USE IT IN BELOW SCRIPTS ***************
-- ******************************** BREAK SCRIPT ****************************************


-- Timing

INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'MONDAY', '1230', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'TUESDAY', '1230', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'WEDNESDAY', '1230', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'THURSDAY', '1230', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'FRIDAY', '1230', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'SATURDAY', '1230', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'SUNDAY', '1230', '1530');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'MONDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'TUESDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'WEDNESDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'THURSDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'FRIDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'SATURDAY', '1900', '2300');
INSERT INTO `Socyal`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'SUNDAY', '1900', '2300');

-- Merchant_QR_Mapping

-- Rewards

-- Merchant_Login


