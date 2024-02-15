package org.example.Controller;

import org.example.Exceptions.ProductException;
import org.example.Exceptions.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

public class ProductController {

    static ProductService productService;
    static SellerService sellerService;

    public ProductController(ProductService productService, SellerService sellerService){
        this.productService = productService;
        this.sellerService = sellerService;
    }

    public Javalin getApi(){

        Javalin api = Javalin.create();

        api.get("/health", ctx -> {ctx.result("Server is up and running!");});

//        api.get("/seller", ctx ->{
//            List<Seller> sellerList = sellerService.getSellerList();
//            ctx.json(sellerList);
//        });
        api.get("seller", ProductController::getAllSellersHandler);
        api.get("/products", ProductController::getAllProductsHandler);
        api.get("/products/{productId}", ProductController::getProductById);

        api.post("/seller", ProductController::postSellerHandler);
        api.post("/products", ProductController::postProductHandler);

        api.delete("/products/{productId}", ProductController::deleteProductByIdHandler);
        api.put("/products/{productId}", ProductController::updateProductByIdHandler);

        return api;
    }

    public static void deleteProductByIdHandler(Context context){
        try {
            long productId = Long.parseLong(context.pathParam("productId"));
            productService.deleteProductById(productId);
            context.status(200);
        }
        catch(Exception e){
            context.status(400);
        }
    }

    public static void getAllProductsHandler(Context context){
        List<Product> productList = productService.getAllProducts();
        context.json(productList);
    }

    public static void getAllSellersHandler(Context context){
        List<Seller> sellerList = sellerService.getSellerList();
        context.json(sellerList);
    }

    public static void postProductHandler(Context context){

        ObjectMapper om =new ObjectMapper();

        try {
            long productId = productService.generateProductId();
            Product p = om.readValue(context.body(), Product.class);
            productService.insertProduct(p, productId);
            context.status(201);
        }
        catch (JsonProcessingException | ProductException e){
            context.result(e.getMessage());
            context.status(400);
        }

    }

    public static void updateProductByIdHandler(Context context){
        ObjectMapper om =new ObjectMapper();

        try {
            long productId = Long.parseLong(context.pathParam("productId"));
            Product p = om.readValue(context.body(), Product.class);
            productService.updateProductById(p, productId);
            context.status(201);
        }
        catch (JsonProcessingException e){
            context.status(400);
        }
        catch(NumberFormatException e){
            context.status(400).result("Product ID is missing in the request");
        } catch (ProductException e) {
            context.status(400).result(e.getMessage());
        }

    }

    public static void postSellerHandler(Context context){
        ObjectMapper om =new ObjectMapper();

        try {
            Seller p = om.readValue(context.body(), Seller.class);
            sellerService.postSeller(p);
            context.status(201);

        } catch (SellerException | JsonProcessingException e) {
            context.result(e.getMessage());
            context.status(400);
        }

    }

    public static void getProductById(Context context){
        try{
        long productId = Long.parseLong(context.pathParam("productId"));
        Product p = productService.getProductById(productId);
        if(p == null){
            context.status(404);
        }
        else{
            context.json(p);
            context.status(200);
        }
    }
        catch(NumberFormatException e){
            context.status(404);
            context.result("Invalid Product Id");
        }
    }

}
