--MySQL
--INSERT INTO users (username, password, enabled, name, apellido, email, telephone) VALUES('andres', '$2a$10$FnDZZcY0trjyFg27oeFs8uojZK5dsnzSxtdTInOMNFHIA2VGx6qhm', 1, 'Andres', 'Guzman', 'Andres.Guzman@gmail.com', '4887304');
--INSERT INTO users (username, password, enabled, name, apellido, email, telephone) VALUES('admin', '$2a$10$8B.x9rTXpvF0f8rFQfNaYup1g1PKiadY1Y3LaBODxAViLiSaL96MG', 1, 'John', 'Doe', 'Jhon.Doe@gmail.com', '4887304');

--PostgreSQL
INSERT INTO users (username, password, enabled, name, last_name, email, telephone) VALUES('andres', '$2a$10$FnDZZcY0trjyFg27oeFs8uojZK5dsnzSxtdTInOMNFHIA2VGx6qhm', true, 'Andres', 'Guzman', 'Andres.Guzman@gmail.com', '4887304');
INSERT INTO users (username, password, enabled, name, last_name, email, telephone) VALUES('admin', '$2a$10$8B.x9rTXpvF0f8rFQfNaYup1g1PKiadY1Y3LaBODxAViLiSaL96MG', true, 'John', 'Doe', 'Jhon.Doe@gmail.com', '5998515');
INSERT INTO users (username, password, enabled, name, last_name, email, telephone) VALUES('user', '$2a$10$7ZPzmHfe2sPJTADQnlJnXeaN5pYU6rTPcQVKQ6Uo3SV8JYJ4cvyJy', true, 'User', 'User', 'User@gmail.com', '3776293');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1);

-- $2a$10$FnDZZcY0trjyFg27oeFs8uojZK5dsnzSxtdTInOMNFHIA2VGx6qhm
-- $2a$10$8B.x9rTXpvF0f8rFQfNaYup1g1PKiadY1Y3LaBODxAViLiSaL96MG
-- $2a$10$dmc9soPupLbBs11QYsOmhuMgz24KY8DDn8W5u8JflVf9p2M1ymbU6
-- $2a$10$2MB/HKnizFt1ln.vYFl.f.tyie2n.SWIl0rGRSdLWgle5ipaIxPFi
