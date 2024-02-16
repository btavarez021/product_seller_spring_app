--ALTER TABLE PRODUCTS DROP CONSTRAINT CONSTRAINT_F2;


DROP TABLE SELLER IF EXISTS;
CREATE TABLE SELLER (
    seller_id varchar(255) primary key,
    seller_name varchar(255) not null
);

DROP TABLE PRODUCTS IF EXISTS;
CREATE TABLE PRODUCTS (
    product_id varchar(255) primary key,
    product_name varchar(255) not null,
    price  NUMERIC(20, 2) not null,
    seller_name varchar(255) not null,
    SELLER_ID varchar(255) references SELLER(seller_id)
);



