# User to modules schema
 
# --- !Ups
 
CREATE TABLE users_modules (
    user_id BIGINT NOT NULL,
    module_id varchar(255) NOT NULL,
    creation_time TIMESTAMP NOT NULL
);
 
# --- !Downs
 
DROP TABLE users_modules;