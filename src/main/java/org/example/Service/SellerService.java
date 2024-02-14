package org.example.Service;

import org.example.Exceptions.SellerException;
import org.example.Model.Seller;
import java.util.ArrayList;
import java.util.List;

public class SellerService {

    List<Seller> sellerList;

    public SellerService(){

        this.sellerList= new ArrayList<>();
    }

    public List<Seller> getSellerList(){

        return sellerList;
    }

    public void postSeller(Seller seller) throws SellerException {

        if(seller.getSellerName().isEmpty()){
            throw new SellerException("You cannot have a blank seller name!");
        }
        if (!doesSellerExist(seller)) {
            sellerList.add(seller);
        }
        else{
            throw new SellerException(seller.getSellerName() + " already exists");
        }
    }

    public boolean doesSellerExist(Seller seller) {

            return sellerList.contains(seller);
    }

}
