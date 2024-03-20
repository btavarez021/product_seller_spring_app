import org.example.DAO.SellerDAO;
import org.example.Util.ConnectionSingleton;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class SellerTesting {

    SellerService sellerService;
    SellerDAO sellerDAO;

    @Before
    public void setUp(){
        ConnectionSingleton.resetTestDatabase();
        Connection conn = ConnectionSingleton.getConnection();
        sellerDAO = new SellerDAO(conn);
        sellerService = new SellerService(sellerDAO);
    }
    @After
    public void tearDown(){
        sellerService.getSellerList().clear();
    }

    @Test
    public void insertSellerTest(){

        String sellerName = "Benny";
        String sellerId = sellerService.generateSellerId();

        Seller s =new Seller(sellerName, sellerId);

        try {
            sellerService.postSeller(s, sellerId);
        } catch (SellerException e) {
            Assert.fail("Unexpected exception occurred");
        }

        List<Seller> updatedSellerList = sellerService.getSellerList();

        //Assert Seller is added
        Assert.assertTrue(updatedSellerList.contains(s));
    }

    @Test
    public void insertProductWithEmptyProductName() {
        String sellerName = "";

        String productId = String.valueOf(UUID.randomUUID());
        Seller s = new Seller(sellerName, productId);

        try {
            sellerService.postSeller(s, productId);
            Assert.fail("Expected Seller Exception due to empty product name");
        } catch (SellerException e) {
            Assert.assertEquals("You cannot have a blank seller name!", e.getMessage());
            Assert.assertTrue(sellerService.getSellerList().isEmpty());
        }

    }


}
