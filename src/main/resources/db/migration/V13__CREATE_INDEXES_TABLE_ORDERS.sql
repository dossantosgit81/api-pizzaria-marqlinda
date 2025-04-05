CREATE INDEX idx_order_status ON Orders(STATUS);
CREATE INDEX idx_order_deliveryman_id ON Orders(DELIVERY_MAN_USER_ID);
CREATE INDEX idx_order_customer_id ON Orders(USER_ID);

