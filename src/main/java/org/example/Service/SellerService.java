package org.example.Service;

import org.example.DAO.SellerDAO;
import org.example.Exceptions.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SellerService {

    SellerDAO sellerDAO;

    public SellerService(SellerDAO sellerDAO){

        this.sellerDAO = sellerDAO;
    }

    public List<Seller> getSellerList(){
        List<Seller> sellerList = sellerDAO.getSellerAllSeller();

        return sellerList;
    }

    public long generateSellerId(){
        UUID uuid = UUID.randomUUID();
        return uuid.getMostSignificantBits() & Long.MAX_VALUE;
    }

    public void postSeller(Seller seller, long sellerId) throws SellerException {
        seller.setSellerId(sellerId);
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

        for(Seller s: sellerDAO.getSellerAllSeller()){
            if(s.getSellerName().equals(seller.getSellerName())){
                return true;
            }
        }
        return false;
    }

    public void deleteSeller(long id){
        for (int i = 0; i < sellerDAO.getSellerAllSeller().size(); i++) {
            Seller currentProduct = sellerDAO.getSellerAllSeller().get(i);
            if (currentProduct.getSellerId() == id) {
                sellerDAO.deleteSellerById(currentProduct);
            }
        }
    }

}
