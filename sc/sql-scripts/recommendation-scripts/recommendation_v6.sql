-- add column LOCALITY.IS_ACTIVE
ALTER TABLE bna.`LOCALITY`
ADD COLUMN `IS_ACTIVE` BIT NULL DEFAULT 0 AFTER `CITY_ID`