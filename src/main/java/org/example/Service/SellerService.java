package org.example.Service;

import org.example.DAO.SellerDAO;
import org.example.Exceptions.SellerException;
import org.example.Model.Seller;
import java.util.ArrayList;
import java.util.List;

public class SellerService {

    SellerDAO sellerDAO;

    public SellerService(SellerDAO sellerDAO){

        this.sellerDAO = sellerDAO;
    }

    public List<Seller> getSellerList(){
        List<Seller> sellerList = sellerDAO.getSellerAllSeller();

        return sellerList;
    }

    public void postSeller(Seller seller) throws SellerException {

        if(seller.getSellerName().isEmpty()){
            throw new SellerException("You cannot have a blank seller name!");
        }
        if (!doesSellerExist(seller)) {
            sellerDAO.insertSeller(seller);
        }
        else{
            throw new SellerException(seller.getSellerName() + " already exists");
        }
    }

    public boolean doesSellerExist(Seller seller) {

            return sellerDAO.getSellerAllSeller().contains(seller);
    }

}
