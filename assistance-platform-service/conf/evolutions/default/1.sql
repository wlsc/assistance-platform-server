# Users schema
 
# --- !Ups
 
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email varchar(255) NOT NULL,
    password varchar(60) NOT NULL
);
 
# --- !Downs
 
DROP TABLE users;