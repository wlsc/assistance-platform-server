# Modules schema
 
# --- !Ups
 
CREATE TABLE active_modules (
    id varchar(255) NOT NULL,
    name varchar(60) NOT NULL,
    logo_url varchar(2048) NOT NULL,
    description_short varchar(255) NOT NULL,
    description_long varchar(2048) NOT NULL,
    required_capabilities varchar(1024) NOT NULL,
    optional_capabilities varchar(1024) NOT NULL,
    copyright varchar(255) NOT NULL,
    ALTER TABLE active_module_localization ADD PRIMARY KEY (module_id, language_code)
);
 
# --- !Downs
 
DROP TABLE active_modules;