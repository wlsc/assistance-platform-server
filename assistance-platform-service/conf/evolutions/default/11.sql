# Add os specific messaging reigstration id
 
# --- !Ups
 
ALTER TABLE devices ADD COLUMN messaging_registration_id varchar(4096);
 
# --- !Downs
 