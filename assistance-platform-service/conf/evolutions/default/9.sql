# Increased size of module capabilite fields (JSON gets stored now)
 
# --- !Ups

ALTER TABLE active_modules ALTER COLUMN required_capabilities TYPE varchar(2048);
ALTER TABLE active_modules ALTER COLUMN optional_capabilities TYPE varchar(2048);
UPDATE active_modules SET required_capabilities = '';
UPDATE active_modules SET optional_capabilities = '';
 
# --- !Downs
 
ALTER TABLE active_modules ALTER COLUMN required_capabilities TYPE varchar(1024);
ALTER TABLE active_modules ALTER COLUMN optional_capabilities TYPE varchar(1024);