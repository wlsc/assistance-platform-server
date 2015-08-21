# Fixed primary key for module localizations 
 
# --- !Ups
 
ALTER TABLE active_module_localization DROP CONSTRAINT active_module_localization_pkey;
ALTER TABLE active_module_localization ADD PRIMARY KEY (module_id, language_code);
 
# --- !Downs
 
ALTER TABLE active_module_localization DROP PRIMARY KEY (module_id, language_code);