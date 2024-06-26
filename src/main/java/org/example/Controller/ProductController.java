package org.example.Controller;

import org.example.Exceptions.ProductException;
import org.example.Exceptions.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    ProductService productService;
    SellerService sellerService;

    public ProductController(ProductService productService, SellerService sellerService){
        this.productService = productService;
        this.sellerService = sellerService;

    }
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> product = productService.getAllProducts();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        // Retrieve sellers by name
        List<Seller> sellers = sellerService.getAllSellersByName(product.getSellerName());

        // Check if any sellers were found
        if (sellers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Use the first seller
        Seller seller = sellers.get(0);

        // Create a new product
        Product newProduct = new Product();
        newProduct.setProductName(product.getProductName());
        newProduct.setPrice(product.getPrice());

        // Associate the product with the seller
        newProduct.getSellers().add(seller);

        // Save the product
        Product savedProduct = productService.saveProduct(newProduct);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        }
        catch(ProductException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
