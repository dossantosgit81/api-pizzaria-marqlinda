insert into carts (ID) values (10);

insert into users (ID, CART_ID, PASSWORD, EMAIL, LAST_NAME, NAME, PHONE) values (10, 10, '$2a$12$nIrC.3vhC7e80MIDvFifkOptKZi4nGg6UiMIBQDHeSiXPe1jEvN/S', 'rafael@gmail.com', 'Santos', 'Rafael', '77940028922');

insert into roles (ID, NAME) values (1, 'COMMON_USER');
insert into roles (ID, NAME) values (2, 'ADMIN_USER');
insert into roles (ID, NAME) values (3, 'CHEF_USER');

insert into users_roles (ROLE_ID, USER_ID) values (1, 10);
insert into users_roles (ROLE_ID, USER_ID) values (2, 10);
insert into users_roles (ROLE_ID, USER_ID) values (3, 10);

INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('RATE_DELIVERY', ARRAY['5.99']);
INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('OPENING_HOURS', ARRAY['08', '00']);
INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('CLOSING_TIME', ARRAY['18', '00']);
INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('OPENING_DAYS', ARRAY['seg.', 'ter.', 'qua.', 'qui.', 'sex.', 'sáb.']);
INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('DELIVERY_FORECAST_MINUTE', ARRAY['90']);
INSERT INTO PAYMENTS_METHODS (ID, TYPE) VALUES (1, 'Cartão de crédito');



