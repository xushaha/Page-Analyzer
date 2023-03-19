-- drop all foreign keys
alter table url_check drop constraint if exists fk_url_check_url_id;
drop index if exists ix_url_check_url_id;

-- drop all
drop table if exists url;

drop table if exists url_check;

