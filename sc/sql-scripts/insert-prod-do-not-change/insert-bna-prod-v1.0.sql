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
INSERT INTO `bna`.`ADDRESS` (`ID`, `ADDRESS`, `LOCALITY_ID`) VALUES (18, 'Plot 15/1, 1st Floor, Sector 2, Opposite Cyber Gateway, Hitech City, Hyderabad', 2);

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
