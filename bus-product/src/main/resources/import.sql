
-- INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Panasonic', 800, NOW(), DATEADD(HOUR, 1, NOW()), 'AUTHORIZED');
-- INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Apple', 1000, NOW(), DATEADD(HOUR, 10, NOW()), 'AUTHORIZED');
-- INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Sony Notebook', 1000, DATEADD(MINUTE, 10, NOW()), NOW(), 'AUTHORIZED');
-- INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Hewlett Packard', 500, NOW(), NOW(), 'AUTHORIZED');
-- INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Bianchi', 600, NOW(), NOW(), 'AUTHORIZED');
-- INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Nike', 100, NOW(), NOW(), 'AUTHORIZED');
-- INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Adidas', 200, NOW(), NOW(), 'AUTHORIZED');
-- INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Reebok', 300, NOW(), NOW(), 'EXPIRED');


INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Panasonic', 800, NOW(), DATEADD(DAY, 1, NOW()), 0);
INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Apple', 1000, NOW(), DATEADD(HOUR, 10, NOW()), 0);
INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Sony Notebook', 1000, DATEADD(MINUTE, 10, NOW()), NOW(), 0);
INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Hewlett Packard', 500, NOW(), NOW(), 0);
INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Bianchi', 600, NOW(), NOW(), 0);
INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Nike', 100, NOW(), NOW(), 0);
INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Adidas', 200, NOW(), NOW(), 0);
INSERT INTO products (name, price, created_at, expiration_on, status) VALUES('Reebok', 300, NOW(), NOW(), 0);