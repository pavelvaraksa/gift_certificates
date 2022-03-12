DROP TABLE IF EXISTS gift_certificate_to_tag;
DROP TABLE IF EXISTS gift_certificate;
DROP TABLE IF EXISTS tag;

create table gift_certificate
(
    id               bigserial
        constraint gift_certificate_pk
            primary key,
    name             varchar(30) not null,
    description      varchar(50) not null,
    price            numeric     not null,
    duration         integer     not null,
    create_date      timestamp   not null,
    last_update_date timestamp   not null
);

create table tag
(
    id   bigserial
        constraint tag_pk
            primary key,
    name varchar(30) not null
);

create table gift_certificate_to_tag
(
    gift_certificate_id bigint not null
        constraint gift_certificate_to_tag_gift_certificate_id_fk
            references gift_certificate,
    tag_id              bigint not null
        constraint gift_certificate_to_tag_tag_id_fk
            references tag
            on delete cascade
);