# Add support email to module information
 
# --- !Ups
 
ALTER TABLE active_modules ADD COLUMN support_email varchar(255);
 
# --- !Downs
 
ALTER TABLE active_modules DROP support_email;