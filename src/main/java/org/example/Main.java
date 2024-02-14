package org.example;
import org.example.Controller.ProductController;
import org.example.Service.ProductService;
import org.example.Service.SellerService;


public class Main {


    public static void main(String[] args) {
        SellerService sellerService = new SellerService();
        ProductService productService = new ProductService(sellerService);
        ProductController productController = new ProductController(productService, sellerService);
        productController.getApi().start(9001);
    }
}
