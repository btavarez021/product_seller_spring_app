import org.example.Exceptions.ProductException;
import org.example.Model.Product;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class ProductTesting {

ProductService productService;
SellerService sellerService;

@Before
public void setUp(){
    sellerService = new SellerService();
    productService = new ProductService(sellerService);
}

@Test
public void insertProductTest(){
List<Product> initialProductServiceList = productService.getAllProducts();
//long productId = (long) (Math.random() * Long.MAX_VALUE);
long productId = productService.generateProductId();

String productName = "Apple Watch";
double price = 799.99;
String sellerName = "Benny";

Product p =new Product(productId, productName, price, sellerName);

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

    Product p =new Product(productId, productName, price, sellerName);

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

    Product p = new Product(productId, productName, price, sellerName);

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

        Product p = new Product(productId, productName, price, sellerName);

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

        System.out.println(productService.doesProductExist(productIdToDelete));
        System.out.println(productService.generateProductId());
        System.out.println(productService.doesProductExist(productIdToDelete));

        Product productToDelete = new Product(productService.generateProductId(), "Apple Watch", 899.99, "Benny");
        productService.insertProduct(productToDelete, productIdToDelete);

        Assert.assertTrue(productService.doesProductExist(productIdToDelete));

        productService.deleteProductById(productIdToDelete);

        Assert.assertFalse(productService.doesProductExist(productIdToDelete));

    }

    @After
    public void tearDown(){
        productService.getAllProducts().clear();
    }

}
