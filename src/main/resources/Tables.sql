DROP TABLE PRODUCTS IF EXISTS;
CREATE TABLE PRODUCTS (
    product_id varchar(255) primary key,
    product_name varchar(255) not null,
    price  NUMERIC(20, 2) not null,
    seller_name varchar(255) not null
);

DROP TABLE SELLER IF EXISTS;
CREATE TABLE SELLER (
    seller_name varchar(255) not null
);
--INSERT INTO ARTIST (productId, productName,price, sellerName)
--VALUES
--(1, 'picasso'),
--(2, 'van gogh');