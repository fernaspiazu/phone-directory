create table phone_directory (
  id              integer not null,
  first_name      varchar(50),
  last_name       varchar(50),
  phone_number    varchar(50),
  constraint pk_phone_dir primary key (id)
);