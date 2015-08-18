# isAlive flag for modules + last feedback timestamp
 
# --- !Ups
 
ALTER TABLE active_modules ADD COLUMN is_alive BOOLEAN;
ALTER TABLE active_modules ADD COLUMN last_alive_message TIMESTAMP;
 
# --- !Downs
 
ALTER TABLE active_modules DROP is_alive;
ALTER TABLE active_modules DROP last_alive_message;