package org.example.Model;

import java.util.Objects;

public class Product {

private long productId;
private String productName;
private double price;
private String sellerName;

public Product(){

}

public Product(long productId, String productName, double price, String sellerName){
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.sellerName = sellerName;
}


    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && Double.compare(price, product.price) == 0 && Objects.equals(productName, product.productName) && Objects.equals(sellerName, product.sellerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, price, sellerName);
    }

    public void setPrice(double price){

    this.price = price;
}

public void setProductName(String productName){
    this.productName = productName;
}

public void setProductId(long productId){
    this.productId = productId;
}
public void setSellerName(String sellerName){
    this.sellerName = sellerName;
}

public long getProductId() {

    return productId;
}


public double getPrice(){
    return price;
}

public String getSellerName(){
    return sellerName;
}

public String getProductName(){return productName;}

}


