create table audit.person_aud (
    id int8 not null,
    rev int4 not null,
    revtype int2,
    gender varchar(255),
    name varchar(255),
    created_by varchar(255),
    created_date timestamp,
    last_modified_by varchar(255),
    last_modified_date timestamp,
    primary key (id, rev)
);
create table audit.revinfo (
    rev int4 not null, revtstmp int8, username VARCHAR(255), primary key (rev)
);
