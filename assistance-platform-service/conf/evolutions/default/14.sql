# Add index for user emails
 
# --- !Ups

CREATE UNIQUE INDEX users_email_unique on users (email);
 
# --- !Downs
 
DROP INDEX users_email_unique;