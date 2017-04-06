-- Locality
INSERT INTO `bna`.`LOCALITY` (`NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('Gachibowli', 'Hyderabad', '17.445118', '78.351962');
INSERT INTO `bna`.`LOCALITY` (`NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('Madhapur', 'Hyderabad', '17.441312', '78.390101');
INSERT INTO `bna`.`LOCALITY` (`NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('Hitech City', 'Hyderabad', '17.446566', '78.377506');
INSERT INTO `bna`.`LOCALITY` (`NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('Jubilee Hils', 'Hyderabad', '17.432503', '78.407282');
INSERT INTO `bna`.`LOCALITY` (`NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('Banjaraa Hills', 'Hyderabad', '17.416324', '78.438591');
INSERT INTO `bna`.`LOCALITY` (`NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('Panjagutta', 'Hyderabad', '17.426436', '78.450858');


-- Address
INSERT INTO `bna`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('Tower 24, Near Gachibowli Flyover, Indiranagar', '1', 'Hyderabad', 'Telangana', 'India', '500098', '17.435560', '78.368031');
INSERT INTO `bna`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('Above KTM Showroom', '2', 'Hyderabad', 'Telangana', 'India', '500098', '17.447865', '78.379048');
INSERT INTO `bna`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('5th Floor, Inorbit Mall', '3', 'Hyderabad', 'Telangana', 'India', '500098', '17.414696', '78.450604');
INSERT INTO `bna`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('Near Jubilee Checkpost', '4', 'Hyderabad', 'Telangana', 'India', '500098', '17.428556', '78.412894');
INSERT INTO `bna`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('Aptech Grand, 5th Floor, Opposite Mercedes Showroom', '5', 'Hyderabad', 'Telangana', 'India', '500098', '17.422653', '78.410963');
INSERT INTO `bna`.`ADDRESS` (`ADDRESS`, `LOCALITY_ID`, `CITY`, `STATE`, `COUNTRY`, `ZIP`, `LATITUDE`, `LONGITUDE`) VALUES ('Dinar Showcase, 7th Floor', '6', 'Hyderabad', 'Telangana', 'India', '500098', '17.432842', '78.446630');

-- Contact
INSERT INTO `bna`.`CONTACT` (`PHONE1`) VALUES ('0407656548767');
INSERT INTO `bna`.`CONTACT` (`PHONE1`) VALUES ('0408374658347');
INSERT INTO `bna`.`CONTACT` (`PHONE1`) VALUES ('0481837682398');
INSERT INTO `bna`.`CONTACT` (`PHONE1`) VALUES ('0409123832600');
INSERT INTO `bna`.`CONTACT` (`PHONE1`) VALUES ('0478623498098');
INSERT INTO `bna`.`CONTACT` (`PHONE1`) VALUES ('0432148716988');

-- Merchant

INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('skyhy-gachibowli', 'Skyhy', '1', '1', 'https://s3.ap-south-1.amazonaws.com/bananaimages/1.png', '3.8', '0', 'Indian, Mughlai, Italian, Chinese', 'Roof Top', 1200.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('fusion-9-hitech-city', 'Fusion 9', '3', '3', 'https://s3.ap-south-1.amazonaws.com/bananaimages/2.png', '3.9', '0', 'Asian, Mexican, Chinese', 'Fine Dining, Bar', 1300.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('news-cafe-hitech-city', 'News Cafe', '3', '3', 'https://s3.ap-south-1.amazonaws.com/bananaimages/3.png', '4.2', '0', 'Chinese, Mughlai, Italian, Continental', 'Fine Dining, Dance Floor', 1500.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('via-milano-jubilee-hills', 'Via Milano', '4', '4', 'https://s3.ap-south-1.amazonaws.com/bananaimages/4.png', '4.1', '0', 'Indian, Asian, Italian', 'Roof Top, Open Bar', 1700.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('urban-asia-jubilee-hills', 'Urban Asia Bar and Grill', '4', '4', 'https://s3.ap-south-1.amazonaws.com/bananaimages/5.png', '4.3', '0', 'Indian, Mughlai, Italian', 'Cafe, Bar', 2000.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('free-flow-jubilee-hills', 'Free Flow', '4', '4', 'https://s3.ap-south-1.amazonaws.com/bananaimages/6.png', '3.2', '0', 'Asian, Mughlai, Chinese', 'Lounge, Roof Top, Bar', 2200.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('pubjab-grill-jubilee-hills', 'Punjab Grill', '4', '4', 'https://s3.ap-south-1.amazonaws.com/bananaimages/7.png', '4.5', '0', 'Mexican, Asian, Italian', 'Open Bar, Roof Top, Dance Floor', 1400.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('rayalaseema-ruchulu-jubilee-jills', 'Rayalaseema Ruchulu', '6', '6', 'https://s3.ap-south-1.amazonaws.com/bananaimages/8.png', '3.6', '0', 'Indian, Mughlai, Chinese, Mexican', 'Roof Top', 1300.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('hashtag-hitech-city', 'Hashtag', '2', '2', 'https://s3.ap-south-1.amazonaws.com/bananaimages/9.png', '3.7', '0', 'Mexican, Asian, Italian', 'Cafe', 1600.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('ticki-shack-jubilee-hills', 'Ticki Shack', '1', '1', 'https://s3.ap-south-1.amazonaws.com/bananaimages/10.png', '4.6', '0', 'Indian, Asian, Chinese', 'Cafe, Bar, Fine Dining', 2500.00);
INSERT INTO `bna`.`MERCHANT` (`NAME_ID`, `NAME`, `ADDRESS_ID`, `CONTACT_ID`, `IMAGE_URL`, `RATING`, `CHECKINS`, `CUISINE`, `TYPE`, `AVERAGE_COST`) VALUES ('soda-bottle-opener-wala-jubilee-hills', 'Soda Bottle Opener Wala', '5', '5', 'https://s3.ap-south-1.amazonaws.com/bananaimages/11.png', '3.3', '0', 'Mexican, Mughlai, Asian, Continental', 'Bar, Kitchen, Bistro', 1600.00);


-- Timing
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('1', 'MONDAY', '1000', '1500');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('1', 'MONDAY', '1800', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('1', 'TUESDAY', '0100', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('1', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('1', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('1', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('1', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('1', 'SUNDAY', '1000', '2100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('2', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('2', 'TUESDAY', '0100', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('2', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('2', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('2', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('2', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('2', 'SUNDAY', '1000', '2100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('3', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('3', 'TUESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('3', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('3', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('3', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('3', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('3', 'SUNDAY', '1000', '2100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('4', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('4', 'TUESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('4', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('4', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('4', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('4', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('4', 'SUNDAY', '1000', '2100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('5', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('5', 'TUESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('5', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('5', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('5', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('5', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('5', 'SUNDAY', '1000', '2100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('6', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('6', 'TUESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('6', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('6', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('6', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('6', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('6', 'SUNDAY', '1000', '2100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('7', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('7', 'TUESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('7', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('7', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('7', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('7', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('7', 'SUNDAY', '1000', '2100');


INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('8', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('8', 'TUESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('8', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('8', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('8', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('8', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('8', 'SUNDAY', '1000', '2100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('9', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('9', 'TUESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('9', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('9', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('9', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('9', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('9', 'SUNDAY', '1000', '2100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('10', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('10', 'TUESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('10', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('10', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('10', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('10', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('10', 'SUNDAY', '1000', '2100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('11', 'MONDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('11', 'TUESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('11', 'WEDNESDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('11', 'THURSDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('11', 'FRIDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('11', 'SATURDAY', '1000', '2100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES ('11', 'SUNDAY', '1000', '2100');

-- Users (Temporary)
INSERT INTO `bna`.`USER` (`UID`, `FIRST_NAME`, `IMAGE_URL`, `EMAIL`, `LAST_NAME`) VALUES ('asd123asd', 'Yogesh', 'https://s3.ap-south-1.amazonaws.com/bananaimages/vikarmchau_sec.png', 'sample@email.com', 'Sadula');
INSERT INTO `bna`.`USER` (`UID`, `FIRST_NAME`, `IMAGE_URL`, `EMAIL`, `LAST_NAME`) VALUES ('asd123asd', 'Xavier', 'https://s3.ap-south-1.amazonaws.com/bananaimages/vikarmchau_sec.png', 'sample@email.com', 'Johnson');
INSERT INTO `bna`.`USER` (`UID`, `FIRST_NAME`, `IMAGE_URL`, `EMAIL`, `LAST_NAME`) VALUES ('asd123asd', 'Krish', 'https://s3.ap-south-1.amazonaws.com/bananaimages/vikarmchau_sec.png', 'sample@email.com', 'Tej');
INSERT INTO `bna`.`USER` (`UID`, `FIRST_NAME`, `IMAGE_URL`, `EMAIL`, `LAST_NAME`) VALUES ('asd123asd', 'Urmila', 'https://s3.ap-south-1.amazonaws.com/bananaimages/vikarmchau_sec.png', 'sample@email.com', 'Mamalla');
INSERT INTO `bna`.`USER` (`UID`, `FIRST_NAME`, `IMAGE_URL`, `EMAIL`, `LAST_NAME`) VALUES ('asd123asd', 'Kriti', 'https://s3.ap-south-1.amazonaws.com/bananaimages/vikarmchau_sec.png', 'sample@email.com', 'Konda');
INSERT INTO `bna`.`USER` (`UID`, `FIRST_NAME`, `IMAGE_URL`, `EMAIL`, `LAST_NAME`) VALUES ('asd123asd', 'Karthik', 'https://s3.ap-south-1.amazonaws.com/bananaimages/vikarmchau_sec.png', 'sample@email.com', 'Putta');
