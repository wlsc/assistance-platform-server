# Add user defined name and last_usage timestamp for devices
 
# --- !Ups
 
ALTER TABLE devices ADD COLUMN user_defined_name VARCHAR(30);
ALTER TABLE devices ADD COLUMN last_usage TIMESTAMP;
 
# --- !Downs
 
ALTER TABLE devices DROP user_defined_name;
ALTER TABLE devices DROP last_usage;