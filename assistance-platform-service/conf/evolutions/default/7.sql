# Add administrator email to module information
 
# --- !Ups
 
ALTER TABLE active_modules ADD COLUMN administrator_email varchar(255);
 
# --- !Downs
 
ALTER TABLE active_modules DROP administrator_email;