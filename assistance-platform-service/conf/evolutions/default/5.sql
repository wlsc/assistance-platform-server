# Localization of module infos, codes regarding ISO 639-1

# --- !Ups
 
CREATE TABLE active_module_localization (
    module_id varchar(255) PRIMARY KEY,
    language_code varchar(2) NOT NULL,
	name varchar(60) NOT NULL,
    logo_url varchar(2048) NOT NULL,
    description_short varchar(255) NOT NULL,
    description_long varchar(2048) NOT NULL
);
 
# --- !Downs
 
DROP TABLE active_module_localization;