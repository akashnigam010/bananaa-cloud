CREATE TABLE bna.`DISH_IMAGE`(
	`ID` INT NOT NULL AUTO_INCREMENT,
	`IMAGE` VARCHAR(100) NOT NULL,
	`THUMBNAIL` VARCHAR(100) NOT NULL,
	`IS_GENERIC` BIT NOT NULL DEFAULT 0,
	PRIMARY KEY (`ID`)
);

INSERT INTO `bna`.`DISH_IMAGE`(`IMAGE`, `THUMBNAIL`, `IS_GENERIC`)
SELECT DISTINCT SUBSTRING(d.IMAGE_URL, length('https://bna-s3.s3.amazonaws.com/')+1, length(d.IMAGE_URL)),
SUBSTRING(d.THUMBNAIL, length('https://bna-s3.s3.amazonaws.com/')+1, length(d.THUMBNAIL)),
CASE WHEN d.IMAGE_URL LIKE '%/item-img/%' THEN 1 ELSE 0 END
FROM DISH d;

ALTER TABLE bna.`DISH`
ADD COLUMN `IMAGE_ID` INT AFTER `IMAGE_URL`,
ADD CONSTRAINT `DISH_IMAGE_FK`
    FOREIGN KEY (`IMAGE_ID`)
    REFERENCES `bna`.`DISH_IMAGE` (`ID`);


UPDATE bna.`DISH` d
INNER JOIN bna.`DISH_IMAGE` i ON d.IMAGE_URL = CONCAT('https://bna-s3.s3.amazonaws.com/', i.IMAGE)
SET d.IMAGE_ID = i.ID;

ALTER TABLE bna.`DISH`
DROP COLUMN `IMAGE_URL`,
DROP COLUMN `THUMBNAIL`;

-- ALTER MERCHANT IMAGE_URL and THUMBNAIL
UPDATE bna.`MERCHANT` m
SET m.IMAGE_URL = SUBSTRING(m.IMAGE_URL, length('https://bna-s3.s3.amazonaws.com/')+1, length(m.IMAGE_URL)),
m.THUMBNAIL = SUBSTRING(m.THUMBNAIL, length('https://bna-s3.s3.amazonaws.com/')+1, length(m.THUMBNAIL));

-- ALTER CUISINE IMAGE_URL and THUMBNAIL
UPDATE bna.`CUISINE` c
SET c.IMAGE_URL = SUBSTRING(c.IMAGE_URL, length('https://s3.ap-south-1.amazonaws.com/bna-s3/')+1, length(c.IMAGE_URL)),
c.THUMBNAIL = SUBSTRING(c.THUMBNAIL, length('https://s3.ap-south-1.amazonaws.com/bna-s3/')+1, length(c.THUMBNAIL));

-- ALTER SUGGESTION IMAGE_URL and THUMBNAIL
UPDATE bna.`SUGGESTION` s
SET s.IMAGE_URL = SUBSTRING(s.IMAGE_URL, length('https://s3.ap-south-1.amazonaws.com/bna-s3/')+1, length(s.IMAGE_URL)),
s.THUMBNAIL = SUBSTRING(s.THUMBNAIL, length('https://s3.ap-south-1.amazonaws.com/bna-s3/')+1, length(s.THUMBNAIL));

-- ALTER USER IMAGE_URL
UPDATE bna.`USER` u
SET u.IMAGE_URL = SUBSTRING(u.IMAGE_URL, length('https://bna-usr.s3.amazonaws.com/')+1, length(u.IMAGE_URL));