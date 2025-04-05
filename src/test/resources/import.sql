insert into carts (ID) values (10);
insert into carts (ID) values (11);
insert into carts (ID) values (12);
insert into carts (ID) values (13);
insert into carts (ID) values (14);

insert into users (ID, CART_ID, PASSWORD, EMAIL, LAST_NAME, NAME, PHONE) values (10, 10, '$2a$12$nIrC.3vhC7e80MIDvFifkOptKZi4nGg6UiMIBQDHeSiXPe1jEvN/S', 'rafael@gmail.com', 'Santos', 'Rafael', '77940028922');
insert into users (ID, CART_ID, PASSWORD, EMAIL, LAST_NAME, NAME, PHONE) values (11, 11, '$2a$12$nIrC.3vhC7e80MIDvFifkOptKZi4nGg6UiMIBQDHeSiXPe1jEvN/S', 'deliveryMan@gmail.com', 'Santos', 'Entregador 1', '77940028922');
insert into users (ID, CART_ID, PASSWORD, EMAIL, LAST_NAME, NAME, PHONE) values (12, 12, '$2a$12$nIrC.3vhC7e80MIDvFifkOptKZi4nGg6UiMIBQDHeSiXPe1jEvN/S', 'delivery2Man@gmail.com', 'Santos', 'Entregador 2', '77940028922');
insert into users (ID, CART_ID, PASSWORD, EMAIL, LAST_NAME, NAME, PHONE) values (13, 13, '$2a$12$nIrC.3vhC7e80MIDvFifkOptKZi4nGg6UiMIBQDHeSiXPe1jEvN/S', 'chef1@gmail.com', 'Santos', 'CHEF 1', '77940028922');
insert into users (ID, CART_ID, PASSWORD, EMAIL, LAST_NAME, NAME, PHONE) values (14, 14, '$2a$12$nIrC.3vhC7e80MIDvFifkOptKZi4nGg6UiMIBQDHeSiXPe1jEvN/S', 'delivery3Man@gmail.com', 'Santos', 'CHEF 1', '77940028922');

insert into roles (ID, NAME) values (1, 'COMMON_USER');
insert into roles (ID, NAME) values (2, 'ADMIN_USER');
insert into roles (ID, NAME) values (3, 'CHEF_USER');
insert into roles (ID, NAME) values (4, 'DELIVERY_MAN_USER');

insert into users_roles (ROLE_ID, USER_ID) values (1, 10);
insert into users_roles (ROLE_ID, USER_ID) values (2, 10);
insert into users_roles (ROLE_ID, USER_ID) values (3, 10);
insert into users_roles (ROLE_ID, USER_ID) values (4, 10);

insert into users_roles (ROLE_ID, USER_ID) values (1, 11);
insert into users_roles (ROLE_ID, USER_ID) values (4, 11);


insert into users_roles (ROLE_ID, USER_ID) values (4, 12);

insert into users_roles (ROLE_ID, USER_ID) values (1, 13);
insert into users_roles (ROLE_ID, USER_ID) values (3, 13);

insert into users_roles (ROLE_ID, USER_ID) values (1, 14);
insert into users_roles (ROLE_ID, USER_ID) values (4, 14);

INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('RATE_DELIVERY', ARRAY['5.99']);
INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('OPENING_HOURS', ARRAY['08', '00']);
INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('CLOSING_TIME', ARRAY['18', '00']);
INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('OPENING_DAYS', ARRAY['seg.', 'ter.', 'qua.', 'qui.', 'sex.', 'sáb.']);
INSERT INTO CONFIGURATIONS (KEY, VALUES) VALUES ('DELIVERY_FORECAST_MINUTE', ARRAY['90']);
INSERT INTO PAYMENTS_METHODS (ID, TYPE) VALUES (1, 'Cartão de crédito');

INSERT INTO ADDRESSES (ID, USER_ID, CITY, NEIGHBORHOOD, STATE, STREET, NUMBER, TITLE, ZIPCODE) VALUES (10, 10, 'São Paulo', 'Jardins', 'SP', 'Av. Paulista', 1000,'Apartamento - Cobertura', '01311-000');
INSERT INTO ORDERS (ID, STATUS, DATE_TIME_ORDER, TOTAL, OBSERVATIONS, DELIVERY, DELIVERY_FORECAST,RATE_DELIVERY, USER_ID, DELIVERY_MAN_USER_ID, ATTENDANT_USER_ID,PAYMENT_METHOD_ID, ADDRESS_ID) VALUES (10, 'ORDER_FOR_DELIVERY', '2025-04-02 12:30:00', 150.75, 'Entregar sem tocar a campainha',TRUE, '2025-04-02 14:00:00', 5.99, 10, null, 13, 1, 10);
INSERT INTO ORDERS (ID, STATUS, DATE_TIME_ORDER, TOTAL, OBSERVATIONS, DELIVERY, DELIVERY_FORECAST,RATE_DELIVERY, USER_ID, DELIVERY_MAN_USER_ID, ATTENDANT_USER_ID,PAYMENT_METHOD_ID, ADDRESS_ID) VALUES (11, 'ORDER_OUT_FOR_DELIVERY', '2025-04-02 12:30:00', 150.75, 'Entregar sem tocar a campainha',TRUE, '2025-04-02 14:00:00', 5.99, 10, 12, 13, 1, 10);
INSERT INTO ORDERS (ID, STATUS, DATE_TIME_ORDER, TOTAL, OBSERVATIONS, DELIVERY, DELIVERY_FORECAST,RATE_DELIVERY, USER_ID, DELIVERY_MAN_USER_ID, ATTENDANT_USER_ID,PAYMENT_METHOD_ID, ADDRESS_ID) VALUES (12, 'ORDER_OUT_FOR_DELIVERY', '2025-04-02 12:30:00', 150.75, 'Entregar sem tocar a campainha',TRUE, '2025-04-02 14:00:00', 5.99, 10, 14, 13, 1, 10);
INSERT INTO ORDERS (ID, STATUS, DATE_TIME_ORDER, TOTAL, OBSERVATIONS, DELIVERY, DELIVERY_FORECAST,RATE_DELIVERY, USER_ID, DELIVERY_MAN_USER_ID, ATTENDANT_USER_ID,PAYMENT_METHOD_ID, ADDRESS_ID) VALUES (14, 'ORDER_IN_PROGRESS', '2025-04-02 12:30:00', 150.75, 'Entregar sem tocar a campainha',TRUE, '2025-04-02 14:00:00', 5.99, 10, 14, 13, 1, 10);

INSERT INTO ORDERS (ID, STATUS, DATE_TIME_ORDER, TOTAL, OBSERVATIONS, DELIVERY, DELIVERY_FORECAST,RATE_DELIVERY, USER_ID, DELIVERY_MAN_USER_ID, ATTENDANT_USER_ID,PAYMENT_METHOD_ID, ADDRESS_ID) VALUES (15, 'ORDER_FOR_DELIVERY', '2025-04-02 12:30:00', 150.75, 'Não é para entrega',FALSE, '2025-04-02 14:00:00', 5.99, 10, null, 13, 1, 10);