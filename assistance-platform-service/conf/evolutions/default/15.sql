# Add index for device users
 
# --- !Ups

CREATE INDEX devices_user on devices (user_id);
 
# --- !Downs
 
DROP INDEX devices_user;