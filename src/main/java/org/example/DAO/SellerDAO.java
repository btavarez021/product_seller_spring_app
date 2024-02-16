package org.example.DAO;

import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SellerDAO {

    Connection conn;

    public SellerDAO(Connection conn){
        this.conn = conn;
    }

    public List<Seller> getSellerAllSeller(){
        List<Seller> getSellerList = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Seller");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String sellerName = rs.getString("seller_name");
                long sellerId = rs.getLong("seller_id");
                Seller s = new Seller(sellerName, sellerId);
                getSellerList.add(s);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("HERE ARE THE GET Seller list " + getSellerList);

        return getSellerList;
    }

    public void insertSeller(Seller s){
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO SELLER (seller_id, seller_name) values (?, ?)");
            ps.setLong(1, s.getSellerId());
            ps.setString(2, s.getSellerName());
            ps.executeUpdate();
            System.out.println("HERE ARE THE INSERT VALUES: " + ps);
        }
        catch (SQLException e ){
            e.printStackTrace();
        }
    }

    public void deleteSellerById(Seller s){
        try{
            PreparedStatement ps = conn.prepareStatement("DELETE FROM SELLER WHERE seller_id=?");
            System.out.println("3: "+ s.getSellerId());
            ps.setLong(1, s.getSellerId());
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


}