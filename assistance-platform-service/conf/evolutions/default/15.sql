# Add index for device users
 
# --- !Ups

CREATE UNIQUE INDEX devices_user on devices (user_id);
 
# --- !Downs
 
DROP INDEX devices_user;