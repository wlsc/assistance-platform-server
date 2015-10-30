# Add index for user module activations
 
# --- !Ups

CREATE INDEX module_users on users_modules (module_id);
 
# --- !Downs
 
DROP INDEX module_users;