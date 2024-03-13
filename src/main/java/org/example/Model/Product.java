package org.example.Model;


import java.util.Objects;

public class Product {

    private String productId;
    private String productName;
    private double price;

    private String sellerId;

    private String sellerName;

    public Product(){

    }

    public Product(String productId, String productName, double price, String sellerName, String sellerId){
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.sellerName = sellerName;
        this.sellerId = sellerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && Double.compare(price, product.price) == 0 && sellerId == product.sellerId && Objects.equals(productName, product.productName) && Objects.equals(sellerName, product.sellerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, price, sellerName, sellerId);
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

    public void setPrice(double price){

    this.price = price;
}

public void setProductName(String productName){
    this.productName = productName;
}

public void setProductId(String productId){
    this.productId = productId;
}

public void setSellerName(String sellerName){
    this.sellerName = sellerName;
}

public String getProductId() {

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


