package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Exceptions.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;
import java.util.List;
import java.util.UUID;

public class ProductService {

    ProductDAO productDAO;
    SellerService sellerService;

    public ProductService(ProductDAO productDAO, SellerService sellerService){
        this.productDAO = productDAO;
        this.sellerService = sellerService;
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

        long sellerId = getSellerIdByName(product.getSellerName().trim());

        String sellerName = product.getSellerName().trim();

        if (!doesSellerExist(sellerName)){
            throw new ProductException("Seller does not exist: " +
                    product.getSellerName());
        }
        product.setSellerId(sellerId);

        productDAO.insertProducts(product);

    }

    private long getSellerIdByName(String sellerName) throws ProductException
    {
        List<Seller> sellers = sellerService.getSellerList();

        for(Seller seller:sellers){
            if(seller.getSellerName().equals(sellerName)){
                return seller.getSellerId();
            }
        }
        throw new ProductException("Seller with name " + sellerName + " not found");
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

//    private void validateSellerName(String sellerName) throws ProductException{
//        if(sellerName.isEmpty()){
//            throw new ProductException("You cannot leave seller name blank!");
//        }
//    }

    private void validatePrice(double price) throws ProductException {
        if(price < 0){
            throw new ProductException("Price cannot be negative!");
        }
    }

    public void validateSellerName(String sellerName) throws ProductException{
        if(sellerName.isEmpty()){
            throw new ProductException("You cannot leave seller name blank!");
        }
    }

    public void deleteProductById(Long id) throws ProductException {

        for (int i = 0; i < productDAO.getAllProducts().size(); i++) {
            Product currentProduct = productDAO.getAllProducts().get(i);
            if (currentProduct.getProductId() == id) {
                productDAO.deleteProductById(currentProduct);
            }
            else{
                throw new ProductException("Product ID "+ id + " does not exist.");
            }
        }
    }

    public void updateProductById(Product product, long productId) throws ProductException {

        String sellerName = product.getSellerName().trim();

        if (!doesSellerExist(sellerName)) {
            throw new ProductException(product.getSellerName() + " does not exist. " +
                    "Please create seller before updating the product.");
        }
        for (int i = 0; i < productDAO.getAllProducts().size(); i++) {
            product.setProductId(productId);
            Product currentProduct = productDAO.getAllProducts().get(i);
            System.out.println(currentProduct.getProductId());
            System.out.println(productId);
            if (currentProduct.getProductId() == productId) {
                if(product.getProductId() != productId){
                    System.out.println(product.getProductId());
                    System.out.println(currentProduct.getProductId());
                    throw new ProductException("Product ID mismatch: " + productId);
            }
            validateProduct(product);
            productDAO.updateProduct(product);
            return;
            }
        }
        throw new ProductException(productId + " does not exist.");

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

    public boolean doesSellerExist(String sellerName) {


        List<Seller> sellers = sellerService.getSellerList();
        if(sellers.isEmpty()){
            return false;
        }
        return sellers.stream().anyMatch(seller -> seller.getSellerName()
                .equalsIgnoreCase(sellerName));
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
