package org.example.Service;

import org.example.Exceptions.ProductException;
import org.example.Model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService {

    List<Product> productList;
    SellerService sellerService;

    public ProductService(SellerService sellerService){
        this.productList = new ArrayList<>();
        this.sellerService = sellerService;
    }

    public List<Product> getAllProducts(){

        return productList;
    }

    public long generateProductId(){
        UUID uuid = UUID.randomUUID();
        return uuid.getMostSignificantBits() & Long.MAX_VALUE;
    }

    public void insertProduct(Product product, long productId) throws ProductException {
//        long productId = (long) (Math.random() * Long.MAX_VALUE);
        product.setProductId(productId);
        validateProduct(product);
        if (doesSellerExist(product)){
            throw new ProductException(product.getSellerName() + " already exists. ");
        }
        productList.add(product);
    }

    private void validateProduct(Product product) throws ProductException {
        validateProductName(product.getProductName());
        validateSellerName(product.getSellerName());
        validatePrice(product.getPrice());
    }
    private void validateProductName(String productName) throws ProductException {
        if(productName.isEmpty()){
            throw new ProductException("You cannot leave product name blank!");
        }
    }

    private void validateSellerName(String sellerName) throws ProductException{
        if(sellerName.isEmpty()){
            throw new ProductException("You cannot leave seller name blank!");
        }
    }

    private void validatePrice(double price) throws ProductException {
        if(price < 0){
            throw new ProductException("Price cannot be negative!");
        }
    }

    public void deleteProductById(Long id) {
        for (int i = 0; i < productList.size(); i++) {
            Product currentProduct = productList.get(i);
            if (currentProduct.getProductId() == id) {
                productList.remove(i);
            }
        }
    }

    public void updateProductById(Product product, Long id){
        for(int i=0; i < productList.size(); i++){
            Product currentProduct = productList.get(i);
            if(currentProduct.getProductId() == id){
                currentProduct.setProductName(product.getProductName());
                currentProduct.setPrice(product.getPrice());
                currentProduct.setSellerName(product.getSellerName());
                break;
            }
            else{
                System.out.println(currentProduct.getProductId() + " does not match "+ id);
            }
        }
    }

    public Product getProductById(Long id){
        for(int i=0; i < productList.size(); i++){
            Product currentProduct = productList.get(i);
            if(currentProduct.getProductId() == id){
                return currentProduct;
            }
        }
        return null;
    }

    public boolean doesSellerExist(Product product) {

        return productList.stream().anyMatch(existingProduct -> existingProduct.getSellerName()
                .equals(product.getSellerName()));
    }

    public boolean doesProductExist(long productId){
        for(Product product:productList){
            System.out.println(productList);
            System.out.println("Product ID: " + product.getProductId());
            System.out.println(productId);
            if(product.getProductId() == productId){
                return true;
            }
        }
        return false;
    }



}
