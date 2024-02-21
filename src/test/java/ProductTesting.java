import org.eclipse.jetty.security.PropertyUserStore;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Exceptions.ProductException;
import org.example.Exceptions.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class ProductTesting {

    Connection conn;
    SellerTesting sellerTesting;

    private ProductDAO productDAO;
    private SellerDAO sellerDAO;
    private ProductService productService;
    private SellerService sellerService;

    @Before
    public void setUp(){
        ConnectionSingleton.resetTestDatabase();
        Connection conn = ConnectionSingleton.getConnection();
        productDAO = new ProductDAO(conn);
        sellerDAO = new SellerDAO(conn);
        sellerService = new SellerService(sellerDAO);
        productService = new ProductService(productDAO, sellerService);

    }

    @Test
    public void insertProductTest(){
        //Generate unique ID's
        long productId = productService.generateProductId();
        long productId2 = productService.generateProductId();
        long sellerId = sellerService.generateSellerId();

        //Create Seller and post it
        String sellerName = "Benny";
        Seller seller = new Seller(sellerName, sellerId);

        try{
            sellerService.postSeller(seller, sellerId);
        }catch(SellerException e){
            Assert.fail("Failed to post seller: "+ e.getMessage());
        }

        //Insert a product
        String productName = "Apple Watch";
        double price = 799.99;

        Product p =new Product(productId, productName, price, sellerName, sellerId);

        try {
            productService.insertProduct(p, productId);
        }
        catch(ProductException e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception occurred "+ e.getMessage());
        }

        //Verify if product exists
        List<Product> updatedProductList = productService.getAllProducts();

        //Assert Product is added
        Assert.assertTrue("Inserted product not found in database", updatedProductList.contains(p));
    }

    @Test
    public void insertProductWithEmptySellerName(){

        //Generate unique ids
        long productId = productService.generateProductId();
        long sellerId = sellerService.generateSellerId();

        //Create Seller and post it
        String sellerName = "Benny";
        Seller seller = new Seller(sellerName, sellerId);

        try{
            sellerService.postSeller(seller, sellerId);
        }catch(SellerException e){
            Assert.fail("Failed to post seller: "+ e.getMessage());
        }

        //create and insert product

        String productName = "Apple Watch";
        double price = 799.99;
        String sellerName2 = "";

        Product p =new Product(productId, productName, price, sellerName2, sellerId);

        try {
            productService.insertProduct(p, productId);
            Assert.fail("Expected Product Exception due to empty seller name");
        }
        catch(ProductException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("You cannot leave seller name blank!", e.getMessage());
            Assert.assertTrue(productService.getAllProducts().isEmpty());
        }

    }
    @Test
    public void insertProductWithEmptyProductName() {
        long productId = productService.generateProductId();
        String productName = "";
        double price = 799.99;
        String sellerName = "Benny";
        long sellerId = sellerService.generateSellerId();

        Product p =new Product(productId, productName, price, sellerName, sellerId);

        try {
            productService.insertProduct(p, productId);
            Assert.fail("Expected Product Exception due to empty product name");
        } catch (ProductException e) {
            Assert.assertEquals("You cannot leave product name blank!", e.getMessage());
            Assert.assertTrue(productService.getAllProducts().isEmpty());
        }

    }
    @Test
    public void insertProductWithSellerThatDoesntExists() {
        //Generate unique ids
        long productId = productService.generateProductId();
        long productId2 = productService.generateProductId();
        long sellerId = sellerService.generateSellerId();

        //Create Seller and post it
        String sellerName = "Benny";
        Seller seller = new Seller(sellerName, sellerId);

        try{
            sellerService.postSeller(seller, sellerId);
        }catch(SellerException e){
            Assert.fail("Failed to post seller: "+ e.getMessage());
        }

        //create a product with same seller and attempt to insert it twice
        String productName = "Apple Watch";
        String sellerName2 = "James";
        double price = 799.99;

        Product p =new Product(productId, productName, price, sellerName, sellerId);
        Product p2 =new Product(productId2, productName, price, sellerName2, sellerId);

        try {
            productService.insertProduct(p, productId);
            productService.insertProduct(p2, productId2);
            Assert.fail("Expected Product Exception due seller name already existing");
        } catch (ProductException e) {
            Assert.assertEquals("Seller with name "+ sellerName2+ " not found", e.getMessage());
            Assert.assertEquals(1, productService.getAllProducts().size());
        }
    }
    @Test
    public void deleteProductWithId() throws ProductException {
        //Generate unique ids
        long productId = productService.generateProductId();
        long sellerId = sellerService.generateSellerId();

        //Create Seller and post it
        String sellerName = "Benny";
        Seller seller = new Seller(sellerName, sellerId);

        try{
            sellerService.postSeller(seller, sellerId);
        }catch(SellerException e){
            Assert.fail("Failed to post seller: "+ e.getMessage());
        }

        //Insert Product
        Product productToDelete = new Product(productService.generateProductId(), "Apple Watch",
                799.99, "Benny", sellerId);
        productService.insertProduct(productToDelete, productId);

        Assert.assertTrue(productService.doesProductExist(productId));

        //Delete product
        productService.deleteProductById(productId);

        //check if product exists after deletion
        Assert.assertFalse(productService.doesProductExist(productId));

    }

    @After
    public void tearDown(){
        productService.getAllProducts().clear();
        ConnectionSingleton.resetTestDatabase();

    }

}