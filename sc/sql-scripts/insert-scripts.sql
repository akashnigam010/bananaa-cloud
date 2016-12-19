-- Locality
INSERT INTO `Socyal`.`LOCALITY` (`ID`, `NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('1', 'Gachibowli', 'Hyderabad', '34.34', '16.34');
INSERT INTO `Socyal`.`LOCALITY` (`ID`, `NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('2', 'Madhapur', 'Hyderabad', '36.34', '16.35');
INSERT INTO `Socyal`.`LOCALITY` (`ID`, `NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('3', 'Hitech City', 'Hyderabad', '32.56', '16.36');
INSERT INTO `Socyal`.`LOCALITY` (`ID`, `NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('4', 'Jubilee Hils', 'Hyderabad', '33.37', '16.37');
INSERT INTO `Socyal`.`LOCALITY` (`ID`, `NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('5', 'Banjaraa Hills', 'Hyderabad', '31.56', '16.38');
INSERT INTO `Socyal`.`LOCALITY` (`ID`, `NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('6', 'Panjagutta', 'Hyderabad', '39.45', '16.39');


-- Address
INSERT INTO `Socyal`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('1', 'Tower 24, Near Gachibowli Flyover, Indiranagar', '1', 'Hyderabad', 'Telangana', 'India', '500098', '17.23', '34.34');

INSERT INTO `Socyal`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('2', 'Above KTM Showroom', '2', 'Hyderabad', 'Telangana', 'India', '500098', '17.23', '34.34');

INSERT INTO `Socyal`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('3', '5th Floor, Inorbit Mall', '3', 'Hyderabad', 'Telangana', 'India', '500098', '17.23', '34.34');

INSERT INTO `Socyal`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('4', 'Near Jubilee Checkpost', '4', 'Hyderabad', 'Telangana', 'India', '500098', '17.23', '34.34');

INSERT INTO `Socyal`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('5', 'Aptech Grand, 5th Floor, Opposite Mercedes Showroom', '5', 'Hyderabad', 'Telangana', 'India', '500098', '17.23', '34.34');

INSERT INTO `Socyal`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('6', 'Dinar Showcase, 7th Floor', '6', 'Hyderabad', 'Telangana', 'India', '500098', '17.23', '34.34');

-- Contact
INSERT INTO `Socyal`.`CONTACT` (`ID`, `PHONE1`) VALUES ('1', '0407656548767');
INSERT INTO `Socyal`.`CONTACT` (`ID`, `PHONE1`) VALUES ('2', '0408374658347');
INSERT INTO `Socyal`.`CONTACT` (`ID`, `PHONE1`) VALUES ('3', '0481837682398');
INSERT INTO `Socyal`.`CONTACT` (`ID`, `PHONE1`) VALUES ('4', '0409123832600');
INSERT INTO `Socyal`.`CONTACT` (`ID`, `PHONE1`) VALUES ('5', '0478623498098');
INSERT INTO `Socyal`.`CONTACT` (`ID`, `PHONE1`) VALUES ('6', '0432148716988');

-- Merchant

INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12345', 'Skyhy', '1', '1', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12345.png', '3.8', '120', 'Indian, Mughlai, Italian, Chinese', 'Roof Top', 1200.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12346', 'Fusion 9', '3', '3', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12346.png', '3.9', '32', 'Asian, Mexican, Chinese', 'Fine Dining, Bar', 1300.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12347', 'News Cafe', '3', '3', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12347.png', '4.2', '233', 'Chinese, Mughlai, Italian, Continental', 'Fine Dining, Dance Floor', 1500.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12348', 'Via Milano', '4', '4', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12348.png', '4.1', '34', 'Indian, Asian, Italian', 'Roof Top, Open Bar', 1700.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12349', 'Urban Asia Bar and Grill', '4', '4', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12349.png', '4.3', '543', 'Indian, Mughlai, Italian', 'Cafe, Bar', 2000.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12350', 'Free Flow', '4', '4', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12350.png', '3.2', '234', 'Asian, Mughlai, Chinese', 'Lounge, Roof Top, Bar', 2200.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12351', 'Punjab Grill', '4', '4', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12351.png', '4.5', '193', 'Mexican, Asian, Italian', 'Open Bar, Roof Top, Dance Floor', 1400.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12352', 'Rayalaseema Ruchulu', '6', '6', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12352.png', '3.6', '541', 'Indian, Mughlai, Chinese, Mexican', 'Roof Top', 1300.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12353', 'Hashtag', '2', '2', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12353.png', '3.7', '4445', 'Mexican, Asian, Italian', 'Cafe', 1600.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12354', 'Ticki Shack', '1', '1', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12354.png', '4.6', '431', 'Indian, Asian, Chinese', 'Cafe, Bar, Fine Dining', 2500.00);
INSERT INTO `Socyal`.`MERCHANT` (`ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('12355', 'Soda Bottle Opener Wala', '5', '5', 'https://s3.ap-south-1.amazonaws.com/bananaimages/12355.png', '3.3', '568', 'Mexican, Mughlai, Asian, Continental', 'Bar, Kitchen, Bistro', 1600.00);


-- Timing

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('1', '12345', 'MONDAY', '1000', '1500');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('2', '12345', 'MONDAY', '1800', '2300');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('3', '12345', 'TUESDAY', '0100', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('4', '12345', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('5', '12345', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('6', '12345', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('7', '12345', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('8', '12345', 'SUNDAY', '1000', '2100');

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('9', '12346', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('10', '12346', 'TUESDAY', '0100', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('11', '12346', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('12', '12346', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('13', '12346', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('14', '12346', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('15', '12346', 'SUNDAY', '1000', '2100');

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('16', '12347', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('17', '12347', 'TUESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('18', '12347', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('19', '12347', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('20', '12347', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('21', '12347', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('22', '12347', 'SUNDAY', '1000', '2100');

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('23', '12348', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('24', '12348', 'TUESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('25', '12348', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('26', '12348', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('27', '12348', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('28', '12348', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('29', '12348', 'SUNDAY', '1000', '2100');

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('30', '12349', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('31', '12349', 'TUESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('32', '12349', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('33', '12349', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('34', '12349', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('35', '12349', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('36', '12349', 'SUNDAY', '1000', '2100');

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('37', '12350', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('38', '12350', 'TUESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('39', '12350', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('40', '12350', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('41', '12350', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('42', '12350', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('43', '12350', 'SUNDAY', '1000', '2100');

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('44', '12351', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('45', '12351', 'TUESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('46', '12351', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('47', '12351', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('48', '12351', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('49', '12351', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('50', '12351', 'SUNDAY', '1000', '2100');


INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('51', '12352', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('52', '12352', 'TUESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('53', '12352', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('54', '12352', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('55', '12352', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('56', '12352', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('57', '12352', 'SUNDAY', '1000', '2100');

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('58', '12353', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('59', '12353', 'TUESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('60', '12353', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('61', '12353', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('62', '12353', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('63', '12353', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('64', '12353', 'SUNDAY', '1000', '2100');

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('65', '12354', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('66', '12354', 'TUESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('67', '12354', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('68', '12354', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('69', '12354', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('70', '12354', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('71', '12354', 'SUNDAY', '1000', '2100');

INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('72', '12355', 'MONDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('73', '12355', 'TUESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('74', '12355', 'WEDNESDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('75', '12355', 'THURSDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('76', '12355', 'FRIDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('77', '12355', 'SATURDAY', '1000', '2100');
INSERT INTO `Socyal`.`TIMING` (`ID`, `MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('78', '12355', 'SUNDAY', '1000', '2100');










