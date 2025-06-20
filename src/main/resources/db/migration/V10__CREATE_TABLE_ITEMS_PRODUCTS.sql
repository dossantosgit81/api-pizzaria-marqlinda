CREATE TABLE IF NOT EXISTS ITEMS_PRODUCTS
(
    ID SERIAL NOT NULL,
    QUANTITY INTEGER NOT NULL,
    SUBTOTAL NUMERIC(10, 2) NOT NULL,
    CART_ID BIGINT,
    ORDER_ID BIGINT,
    PRODUCT_ID BIGINT NOT NULL,
    CONSTRAINT ITEMS_CART_PK PRIMARY KEY (ID),
    CONSTRAINT CART_ITEM_FK FOREIGN KEY (CART_ID) REFERENCES CARTS(ID),
    CONSTRAINT PRODUCT_ITEM_FK FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS(ID),
    CONSTRAINT ITEMS_PRODUCTS_ORDERS_FK FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID)
);
