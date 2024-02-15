package org.example.DAO;

import org.example.Model.Product;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    Connection conn;

    public ProductDAO(Connection conn){

        this.conn = conn;
    }

    public List<Product> getAllProducts(){

        List<Product> productResults = new ArrayList<>();

        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Products");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                long productId = rs.getLong("product_id");
                String productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                String sellerName = rs.getString("seller_name");
                Product p = new Product(productId, productName, price, sellerName);
                productResults.add(p);
            }

        }
        catch(SQLException e)  {
            e.printStackTrace();
        }

        return productResults;
    }

    public void insertProducts(Product p){

        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Products (product_id, product_name, price, seller_name)" +
                    " values (?,?,?,?)");
            ps.setLong(1, p.getProductId());
            ps.setString(2,p.getProductName());
            ps.setDouble(3,p.getPrice());
            ps.setString(4,p.getSellerName());
            ps.executeUpdate();
        }
        catch(SQLException e ){
            e.printStackTrace();
        }

    }

    public void updateProduct(Product p){
         try{
             PreparedStatement ps = conn.prepareStatement("UPDATE Products SET product_name=?, " +
                     "price=?, seller_name=? where product_id=?");
             ps.setString(1,p.getProductName());
             ps.setDouble(2,p.getPrice());
             ps.setString(3,p.getSellerName());
             ps.setLong(4, p.getProductId());
             ps.executeUpdate();
         }
         catch(SQLException e){

             e.printStackTrace();

         }

    }


}
