DELETE FROM author
insert into author(f_name, l_name) values ('George', 'Orwell'), ('J. R. R.', 'Tolkien'), ('J. K.', 'Rowling'), ('George R. R.', 'Martin'), ('Aldous', 'Huxley')

insert into "user"(id, name, password) values (1, 'admin', '$2a$10$aQ2ThAxW6IUkjwdhBmuwW.kLTKuMgdLEJI4k27RiToXOKp3Bd6gS2')

insert into role(id, code) values (1, 'ADMIN')
insert into role(id, code) values (2, 'MODERATOR')
insert into role(id, code) values (3, 'USER')

insert into user_roles(user_id, role_id) values (1, 1)
insert into user_roles(user_id, role_id) values (1, 2)
insert into user_roles(user_id, role_id) values (1, 3)

insert into publisher(id, name) values (1, 'Bloomsbury Publishing PLC')
insert into publisher(id, name) values (2, 'Penguin Books Ltd')

insert into book(isbn, age_range, bestsellers_rank, depth, height, weight, width, format, imprint, language, publication_date, city, country, title, author_id, publisher_id) values (1408866196, '[9-unbounded', 6969, 25, 198, 321, 129, 'Hardback | 272 pages', 'Bloomsbury Childrens Books', 'en', '2015-01-29 00:00:00', 'London', 'United Kingdom','Harry Potter and the Philosopher''s Stone', 3, 1);
insert into book(isbn, title, author_id, bestsellers_rank, publisher_id) values (9780141036144, '1984', 1, 95, 2)
