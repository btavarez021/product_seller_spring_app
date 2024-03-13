package org.example.Model;

import java.util.Objects;

public class Seller {

    String sellerId;
    String sellerName;


    public Seller(){

    }


    public Seller(String sellerName, String sellerId){

        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }

    public String getSellerName() {

        return sellerName.trim();
    }

    public void setSellerName(String sellerName) {

        this.sellerName = sellerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return sellerId == seller.sellerId && Objects.equals(sellerName, seller.sellerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerName, sellerId);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String productId) {
        this.sellerId = productId;
    }

}
