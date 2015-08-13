# User devices

# --- !Ups
 
CREATE TABLE devices (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    os varchar(30) NOT NULL,
    os_version varchar(30) NOT NULL,
    device_identifier varchar(256) NOT NULL,
    brand varchar(30) NOT NULL, /* Könnte man ggf. auch mit einer zweiten Tabelle Brands machen, aber aktuell nicht nötig */
    model varchar(30) NOT NULL
);
 
# --- !Downs
 
DROP TABLE devices;