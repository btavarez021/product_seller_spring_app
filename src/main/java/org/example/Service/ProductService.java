package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Exceptions.ProductException;
import org.example.Model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService {

    ProductDAO productDAO;

    public ProductService(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts(){
        List<Product> productList = productDAO.getAllProducts();
        return productList;
    }

    public long generateProductId(){
        UUID uuid = UUID.randomUUID();
        return uuid.getMostSignificantBits() & Long.MAX_VALUE;
    }

    public void insertProduct(Product product, long productId) throws ProductException {
        product.setProductId(productId);
        validateProduct(product);
        if (doesSellerExist(product)){
            throw new ProductException(product.getSellerName() + " already exists. ");
        }
        productDAO.insertProducts(product);
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
        for (int i = 0; i < productDAO.getAllProducts().size(); i++) {
            Product currentProduct = productDAO.getAllProducts().get(i);
            if (currentProduct.getProductId() == id) {
                productDAO.getAllProducts().remove(i);
            }
        }
    }

    public void updateProductById(Product product, long productId) throws ProductException {

        validateProduct(product);

        for(int i=0; i < productDAO.getAllProducts().size(); i++){
            Product currentProduct = productDAO.getAllProducts().get(i);
            product.setProductId(productId);
            if(currentProduct.getProductId() == productId){
                currentProduct.setProductName(product.getProductName());
                currentProduct.setPrice(product.getPrice());
                currentProduct.setSellerName(product.getSellerName());
                productDAO.updateProduct(product);
                break;
            }
            else{
                System.out.println(currentProduct.getProductId() + " does not match "+ productId);
            }
            System.out.println("CURRENT PRODUCT: "+ currentProduct);

        }
    }

    public Product getProductById(Long id){
        for(int i=0; i < productDAO.getAllProducts().size(); i++){
            Product currentProduct = productDAO.getAllProducts().get(i);
            if(currentProduct.getProductId() == id){
                return currentProduct;
            }
        }
        return null;
    }

    public boolean doesSellerExist(Product product) {

        return productDAO.getAllProducts().stream().anyMatch(existingProduct -> existingProduct.getSellerName()
                .equals(product.getSellerName()));
    }

    public boolean doesProductExist(long productId){
        for(Product product:productDAO.getAllProducts()){
            if(product.getProductId() == productId){
                return true;
            }
        }
        return false;
    }



}
