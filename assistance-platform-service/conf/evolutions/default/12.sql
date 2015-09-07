# Increase size of model column of devices
 
# --- !Ups

ALTER TABLE devices ALTER COLUMN model TYPE varchar(100);
 
# --- !Downs
 