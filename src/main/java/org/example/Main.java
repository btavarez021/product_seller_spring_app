package org.example;
import org.example.Controller.ProductController;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;

import java.sql.Connection;


public class Main {


    public static void main(String[] args) {
        Connection conn = ConnectionSingleton.getConnection();
        SellerDAO sellerDAO = new SellerDAO(conn);
        ProductDAO productDAO = new ProductDAO(conn);

        SellerService sellerService = new SellerService(sellerDAO);
        ProductService productService = new ProductService(productDAO);

        ProductController productController = new ProductController(productService, sellerService);
        productController.getApi().start(9001);
    }
}
