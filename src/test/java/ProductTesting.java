import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Exceptions.ProductException;
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

    private ProductDAO productDAO;
    private SellerDAO sellerDAO;
    private ProductService productService;
    private SellerService sellerService;

    @Before
    public void setUp(){
        // Initialize your ProductDAO, ProductService, and SellerService here
        ConnectionSingleton.resetTestDatabase();
        Connection conn = ConnectionSingleton.getConnection();
        productDAO = new ProductDAO(conn);
        sellerDAO = new SellerDAO(conn);
        productService = new ProductService(productDAO, sellerService);
        sellerService = new SellerService(sellerDAO);
    }

    @Test
    public void insertProductTest(){
        List<Product> initialProductServiceList = productDAO.getAllProducts();
//long productId = (long) (Math.random() * Long.MAX_VALUE);
        long productId = productService.generateProductId();
        String productName = "Apple Watch";
        double price = 799.99;
        String sellerName = "Benny";
        long sellerId = sellerService.generateSellerId();

        Product p =new Product(productId, productName, price, sellerName, sellerId);

        try {
            productService.insertProduct(p, productId);
        }
        catch(ProductException e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception occurred");
        }

        List<Product> updatedProductList = productService.getAllProducts();

        //Assert Product is added
        Assert.assertTrue(updatedProductList.contains(p));
    }

    @Test
    public void insertProductWithEmptySellerName(){
        long productId = productService.generateProductId();
        String productName = "Apple Watch";
        double price = 799.99;
        String sellerName = "";
        long sellerId = sellerService.generateSellerId();

        Product p =new Product(productId, productName, price, sellerName, sellerId);

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
            System.out.println(e.getMessage());
            Assert.assertEquals("You cannot leave product name blank!", e.getMessage());
            Assert.assertTrue(productService.getAllProducts().isEmpty());
        }

    }
    @Test
    public void insertProductWithSellerThatExists() {
//        long productId = (long) (Math.random() * Long.MAX_VALUE);
        long productId = productService.generateProductId();
        String productName = "Apple Watch";
        double price = 799.99;
        String sellerName = "Benny";
        long sellerId = sellerService.generateSellerId();

        Product p =new Product(productId, productName, price, sellerName, sellerId);

        try {
            productService.insertProduct(p, productId);
            productService.insertProduct(p, productId);
            Assert.fail("Expected Product Exception due seller name already existing");
        } catch (ProductException e) {
            Assert.assertEquals(sellerName + " already exists. ", e.getMessage());
            Assert.assertEquals(1, productService.getAllProducts().size());
        }
    }
    @Test
    public void deleteProductWithId() throws ProductException {
        long productIdToDelete = productService.generateProductId();
        long sellerId = sellerService.generateSellerId();

        Seller createSeller = new Seller("Benny", sellerId);
        Product productToDelete = new Product(productService.generateProductId(), "Apple Watch",
                899.99, "Benny", sellerId);
        productService.insertProduct(productToDelete, productIdToDelete);

        Assert.assertTrue(productService.doesProductExist(productIdToDelete));

        productService.deleteProductById(productIdToDelete);

        Assert.assertFalse(productService.doesProductExist(productIdToDelete));

    }

    @After
    public void tearDown(){
        productService.getAllProducts().clear();
        ConnectionSingleton.resetTestDatabase();

    }

}