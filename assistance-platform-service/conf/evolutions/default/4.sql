# User Profile information
 
# --- !Ups
 
ALTER TABLE users ADD COLUMN firstname varchar(40);
ALTER TABLE users ADD COLUMN lastname varchar(40);
ALTER TABLE users ADD COLUMN joined_since TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE users ADD COLUMN last_login TIMESTAMP;
 
# --- !Downs
 
ALTER TABLE users DROP joined_since;
ALTER TABLE users DROP last_login;
ALTER TABLE users DROP firstname;
ALTER TABLE users DROP lastname;