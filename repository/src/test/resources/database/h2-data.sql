INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Certificate_1', 'Description_1', 1, 1, '2022-02-01 13:01:22.126',
        '2022-02-01 13:01:22.126'),
       ('Certificate_2', 'Description_2', 2, 2, '2022-02-02 13:01:22.126',
        '2022-02-02 13:01:22.126'),
       ('Certificate_3', 'Description_3', 3, 3, '2022-02-03 13:01:22.126',
        '2022-02-03 13:01:22.126');

INSERT INTO tag (name)
VALUES ('epam'),
       ('gift'),
       ('gym');

INSERT INTO gift_certificate_to_tag (gift_certificate_id, tag_id)
VALUES (2, 3),
       (2, 2),
       (3, 1),
       (3, 2),
       (3, 3)