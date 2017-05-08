-- Locality
INSERT INTO `bna`.`LOCALITY` (`NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('Hitech City', 'Hyderabad', '17.443484', '78.377130');
INSERT INTO `bna`.`LOCALITY` (`NAME`, `CITY`, `LATITUDE`, `LONGITUDE`) VALUES ('Jubilee Hils', 'Hyderabad', '17.426559', '78.415532');

-- contact
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('1', '04069993377');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('2', '08008050005');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('3', '04039561400');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('4', '04033165315');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('5', '04065503636');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('6', '04040126801');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('7', '07675043686');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('8', '04023123123');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('9', '07675043686');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('10', '04040048484');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('11', '04066661199');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('12', '04039999500');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('13', '04065599122');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('14', '04064444567');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('15', '04040101237');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('16', '04064514444');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('17', '08790155588');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('18', '04033165061');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('19', '09100920233 ');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('20', '09966132307');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('21', '04069000258');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('22', '04023116606');
INSERT INTO `bna`.`CONTACT` (`ID`, `PHONE1`) VALUES ('23', '09000911122');

-- address
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (1, '5th Floor, Inorbit Mall, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (2, '1st Floor, Shop 2, 1-140/2&3, Opposite Raheja Mind Space, Sector 3, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (3, 'Phoenix Tower A, Opposite Trident Hotel, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (4, 'Second Floor, Phoenix Tower A, Opposite Trident Hotel, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (5, '5th Floor, Phoenix Tower A, Opposite Trident Hotel, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (6, 'F 48, 1st Floor, Inorbit Mall, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (7, 'First Floor, Shilparamam Complex, Opposite Cyber Tower, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (8, 'Plot 73, Jubilee Enclave, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (9, 'First Floor, Shilparamam Complex, Opposite Cyber Tower, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (10, '4th Floor, Food Court, Inorbit Mall, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (11, 'Beside Shilpa Kalavedika, Opposite Cyber Tower, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (12, '3rd Floor, BK Towers, Near Cyber Towers, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (13, 'Plot 1 & 2, Rohini Layout, Opposite Shilparamam, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (14, 'Plot 18, Sector 2, Hardhick Crown, Opposite Cyber Pearl, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (15, '2nd & 3rd Floor, Inorbit Mall, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (16, '8-3-293/82/A/70, 4th Floor, Anshu Colours, Near TV5 Office, Jubilee Hills, Hyderabad', 2);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (17, '8-2-293/82/A/1217, Plot 1217, Shreshta Aura, Road 36, Jubilee Hills, Hyderabad', 2);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (18, 'Plot 15/1, 1st Floor, Sector 2, Opposite Cyber Gateway, Hitech City, Hyderabad', 1);
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (19, 'Adityaram Square, 5th Floor, Door 8-2-293/82A646/A Road 36, Jubilee Hills, Hyderabad', 2);

-- merchant
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (1, 'Fusion 9', 'fusion-9-hitech-city', 1, 1, 'Roof Top', 1200.00, 'http://cdn.bananaa.in/rest-img/fusion-9-hitech-city.jpg', 'http://cdn.bananaa.in/rest-img/t/fusion-9-hitech-city.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (2, 'Sociial District', 'sociial-district-hitech-city', 2, 2, 'Casual Dining', 1300.00, 'http://cdn.bananaa.in/rest-img/sociial-district-hitech-city.jpg', 'http://cdn.bananaa.in/rest-img/t/sociial-district-hitech-city.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (3, 'Junior Kuppanna', 'junior-kuppanna-hitech-city', 3, 3, 'Casual Dining', 650.00, 'http://cdn.bananaa.in/rest-img/junior-kuppanna-hitech-city.jpg', 'http://cdn.bananaa.in/rest-img/t/junior-kuppanna-hitech-city.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (4, 'Habanero', 'habanero-hitech-city', 4, 4, 'Casual Dining, Bar', 1800.00, 'http://cdn.bananaa.in/rest-img/habanero-hitech-city.jpg', 'http://cdn.bananaa.in/rest-img/t/habanero-hitech-city.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (12, 'Little Italy', 'little-italy-hitech-city', 12, 12, 'Fine Dining', 1750.00, 'http://cdn.bananaa.in/rest-img/little-italy-hitech-city.jpg', 'http://cdn.bananaa.in/rest-img/t/little-italy-hitech-city.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (14, 'Tabla', 'tabla-hitech-city', 14, 14, 'Casual Dining', 900.00, 'http://cdn.bananaa.in/rest-img/tabla-hitech-city.jpg', 'http://cdn.bananaa.in/rest-img/t/tabla-hitech-city.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (16, 'Siaa', 'siaa-jubilee-hills', 16, 16, 'Bar, Casual Dining', 1200.00, 'http://cdn.bananaa.in/rest-img/siaa-jubilee-hills.jpg', 'http://cdn.bananaa.in/rest-img/t/siaa-jubilee-hills.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (17, 'Spoil', 'spoil-jubilee-hills', 16, 17, 'Club', 2000.00, 'http://cdn.bananaa.in/rest-img/spoil-jubilee-hills.jpg', 'http://cdn.bananaa.in/rest-img/t/spoil-jubilee-hills.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (18, 'Uberdeq', 'uberdeq-jubilee-hills', 16, 18, 'Pub', 2000.00, 'http://cdn.bananaa.in/rest-img/uberdeq-jubilee-hills.jpg', 'http://cdn.bananaa.in/rest-img/t/uberdeq-jubilee-hills.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (19, 'Quattro Ristorante', 'quattro-ristorante-jubilee-hills', 17, 19, 'Casual Dining', 2000.00, 'http://cdn.bananaa.in/rest-img/quattro-ristorante-jubilee-hills.jpg', 'http://cdn.bananaa.in/rest-img/t/quattro-ristorante-jubilee-hills.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (20, 'Spice Klub', 'spiceklub-jubilee-hills', 17, 20, 'Casual Dining', 1600.00, 'http://cdn.bananaa.in/rest-img/spiceklub-jubilee-hills.jpg', 'http://cdn.bananaa.in/rest-img/t/spiceklub-jubilee-hills.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (21, 'Desi Klub', 'desiklub-jubilee-hills', 17, 21, 'Casual Dining, Bar', 1500.00, 'http://cdn.bananaa.in/rest-img/desiklub-jubilee-hills.jpg', 'http://cdn.bananaa.in/rest-img/t/desiklub-jubilee-hills.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (22, 'Tabla Sapphire', 'tabla-sapphire-hitech-city', 14, 14, 'Lounge, Bar', 900.00, 'http://cdn.bananaa.in/rest-img/tabla-sapphire-hitech-city.jpg', 'http://cdn.bananaa.in/rest-img/t/tabla-sapphire-hitech-city.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (23, 'Whiteboard Cafe', 'whiteboard-cafe-hitech-city', 18, 22, 'Cafe', 600.00, 'http://cdn.bananaa.in/rest-img/whiteboard-cafe-hitech-city.jpg', 'http://cdn.bananaa.in/rest-img/t/whiteboard-cafe-hitech-city.jpg');
INSERT INTO `bna`.`MERCHANT` (`ID`, `NAME`, `NAME_ID`, `ADDRESS_ID`, `CONTACT_ID`, `TYPE`, `AVERAGE_COST`, `IMAGE_URL`, `THUMBNAIL`) VALUES (24, 'Area Ten - Seventy', 'area-ten-seventy-jubilee-hills', 19, 23, 'Cafe', 650.00, 'http://cdn.bananaa.in/rest-img/area-ten-seventy-jubilee-hills.jpg', 'http://cdn.bananaa.in/rest-img/t/area-ten-seventy-jubilee-hills.jpg');

-- timings
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'MONDAY', '1200', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'TUESDAY', '1200', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'WEDNESDAY', '1200', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'THURSDAY', '1200', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'FRIDAY', '1200', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'SATURDAY', '1200', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (1, 'SUNDAY', '1200', '2400');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'MONDAY', '1200', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'TUESDAY', '1200', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'WEDNESDAY', '1200', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'THURSDAY', '1200', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'FRIDAY', '1200', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'SATURDAY', '1200', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (2, 'SUNDAY', '1200', '2300');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'MONDAY', '1200', '1600');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'MONDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'TUESDAY', '1200', '1600');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'TUESDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'WEDNESDAY', '1200', '1600');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'WEDNESDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'THURSDAY', '1200', '1600');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'THURSDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'FRIDAY', '1200', '1600');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'FRIDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'SATURDAY', '1200', '1600');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'SATURDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'SUNDAY', '1200', '1600');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (3, 'SUNDAY', '1900', '2300');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'MONDAY', '1130', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'MONDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'TUESDAY', '1130', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'TUESDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'WEDNESDAY', '1130', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'WEDNESDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'THURSDAY', '1130', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'THURSDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'FRIDAY', '1130', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'FRIDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'SATURDAY', '1130', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'SATURDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'SUNDAY', '1130', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (4, 'SUNDAY', '1830', '2330');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'MONDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'MONDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'TUESDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'TUESDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'WEDNESDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'WEDNESDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'THURSDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'THURSDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'FRIDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'FRIDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'SATURDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'SATURDAY', '1900', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'SUNDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (12, 'SUNDAY', '1900', '2300');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (14, 'MONDAY', '1200', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (14, 'TUESDAY', '1200', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (14, 'WEDNESDAY', '1200', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (14, 'THURSDAY', '1200', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (14, 'FRIDAY', '1200', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (14, 'SATURDAY', '1200', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (14, 'SUNDAY', '1200', '2330');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'MONDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'MONDAY', '1800', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'TUESDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'TUESDAY', '1800', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'WEDNESDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'WEDNESDAY', '1800', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'THURSDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'THURSDAY', '1800', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'FRIDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'FRIDAY', '1800', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'SATURDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'SATURDAY', '1800', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'SUNDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (16, 'SUNDAY', '1800', '2330');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (17, 'MONDAY', '1500', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (17, 'TUESDAY', '1500', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (17, 'WEDNESDAY', '1500', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (17, 'THURSDAY', '1500', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (17, 'FRIDAY', '1500', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (17, 'SATURDAY', '1500', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (17, 'SUNDAY', '1500', '2400');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (18, 'MONDAY', '1200', '0100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (18, 'TUESDAY', '1200', '0100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (18, 'WEDNESDAY', '1200', '0100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (18, 'THURSDAY', '1200', '0100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (18, 'FRIDAY', '1200', '0100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (18, 'SATURDAY', '1200', '0100');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (18, 'SUNDAY', '1200', '0100');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'MONDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'MONDAY', '1830', '2345');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'TUESDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'TUESDAY', '1830', '2345');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'WEDNESDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'WEDNESDAY', '1830', '2345');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'THURSDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'THURSDAY', '1830', '2345');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'FRIDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'FRIDAY', '1830', '2345');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'SATURDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'SATURDAY', '1830', '2345');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'SUNDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (19, 'SUNDAY', '1830', '2345');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'MONDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'MONDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'TUESDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'TUESDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'WEDNESDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'WEDNESDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'THURSDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'THURSDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'FRIDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'FRIDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'SATURDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'SATURDAY', '1830', '2330');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'SUNDAY', '1200', '1530');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (20, 'SUNDAY', '1830', '2330');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (21, 'MONDAY', '1400', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (21, 'TUESDAY', '1400', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (21, 'WEDNESDAY', '1400', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (21, 'THURSDAY', '1400', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (21, 'FRIDAY', '1400', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (21, 'SATURDAY', '1400', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (21, 'SUNDAY', '1400', '2400');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (22, 'MONDAY', '1100', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (22, 'TUESDAY', '1100', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (22, 'WEDNESDAY', '1100', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (22, 'THURSDAY', '1100', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (22, 'FRIDAY', '1100', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (22, 'SATURDAY', '1100', '2400');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (22, 'SUNDAY', '1100', '2400');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (23, 'MONDAY', '0800', '2230');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (23, 'TUESDAY', '0800', '2230');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (23, 'WEDNESDAY', '0800', '2230');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (23, 'THURSDAY', '0800', '2230');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (23, 'FRIDAY', '0800', '2230');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (23, 'SATURDAY', '0800', '2230');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (23, 'SUNDAY', '0800', '2230');

INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (24, 'MONDAY', '1030', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (24, 'TUESDAY', '1030', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (24, 'WEDNESDAY', '1030', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (24, 'THURSDAY', '1030', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (24, 'FRIDAY', '1030', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (24, 'SATURDAY', '1030', '2300');
INSERT INTO `bna`.`TIMING` (`MERCHANT_ID`, `DAY`, `OPEN`, `CLOSE`) VALUES (24, 'SUNDAY', '1030', '2300');

