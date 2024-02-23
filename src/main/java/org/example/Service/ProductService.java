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

        product.setSellerId(sellerId);

        if (doesSellerExist(product)){
            productDAO.insertProducts(product);
        }
//        else{
//            throw new ProductException(product.getSellerName() + " already exists. ");
//
//        }

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

        if (doesSellerExist(product)) {
            for (int i = 0; i < productDAO.getAllProducts().size(); i++) {
                Product currentProduct = productDAO.getAllProducts().get(i);
                product.setProductId(productId);
                if (currentProduct.getProductId() == productId) {
                    validateProduct(product);
                    currentProduct.setProductName(product.getProductName());
                    currentProduct.setPrice(product.getPrice());
                    currentProduct.setSellerName(product.getSellerName());
                    productDAO.updateProduct(product);
                    break;
                } else {
                    throw new ProductException(productId + " does not exist.");
                }
            }
        }
        else {
                throw new ProductException(product.getSellerName() + " does not exist. " +
                        "Please create seller before updating the product.");

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

//        System.out.println("2: " + product.getSellerName());
//
//        for(Product p:productDAO.getAllProducts()){
//            System.out.println("1: " + p.getSellerName());
//        }
//
//        System.out.println(productDAO.getAllProducts().stream().anyMatch(existingProduct -> existingProduct.getSellerName()
//                .equals(product.getSellerName())));

        return productDAO.getAllProducts().stream().allMatch(existingProduct -> existingProduct.getSellerName()
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
