DELETE FROM author
insert into author(f_name, l_name) values ('George', 'Orwell'), ('J. R. R.', 'Tolkien'), ('J. K.', 'Rowling'), ('George R. R.', 'Martin'), ('Aldous', 'Huxley')

insert into "user"(id, name, password) values (1, 'admin', '$2a$10$aQ2ThAxW6IUkjwdhBmuwW.kLTKuMgdLEJI4k27RiToXOKp3Bd6gS2')

insert into role(id, code) values (1, 'ADMIN')
insert into role(id, code) values (2, 'MODERATOR')
insert into role(id, code) values (3, 'USER')

insert into user_roles(user_id, role_id) values (1, 1)
insert into user_roles(user_id, role_id) values (1, 2)
insert into user_roles(user_id, role_id) values (1, 3)