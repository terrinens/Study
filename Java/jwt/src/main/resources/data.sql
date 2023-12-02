insert into user (username, password, nickname, activated) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);
insert into user (username, password, nickname, activated) values ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1);

insert into user_authority (user_id, authority_name) values (1, 'ADMIN');
insert into user_authority (user_id, authority_name) values (2, 'USER');

insert into authority (authority_name) values ('USER');
insert into authority (authority_name) values ('ADMIN');
