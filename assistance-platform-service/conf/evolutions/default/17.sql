# Add REST contact address field to modules
 
# --- !Ups
 
ALTER TABLE active_modules ADD COLUMN rest_contact_address varchar(50);
 
# --- !Downs
 