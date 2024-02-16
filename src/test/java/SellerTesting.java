import org.example.DAO.SellerDAO;
import org.example.Exceptions.SellerException;
import org.example.Model.Seller;
import org.example.Service.SellerService;
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

    Connection conn;

    @Before
    public void setUp(){
        sellerDAO = new SellerDAO(conn);
        sellerService = new SellerService(sellerDAO);
    }
    @After
    public void tearDown(){
        sellerService.getSellerList().clear();
    }

    @Test
    public void insertProductTest(){

        UUID uuid = UUID.randomUUID();

        String sellerName = "Benny";
        long productId = uuid.getMostSignificantBits() & Long.MAX_VALUE;


        Seller s =new Seller(sellerName, productId);

        try {
            sellerService.postSeller(s, productId);
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
        UUID uuid = UUID.randomUUID();

        long productId = uuid.getMostSignificantBits() & Long.MAX_VALUE;
        Seller s = new Seller(sellerName, productId);

        try {
            sellerService.postSeller(s, productId);
            Assert.fail("Expected Seller Exception due to empty product name");
        } catch (SellerException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("You cannot have a blank seller name!", e.getMessage());
            Assert.assertTrue(sellerService.getSellerList().isEmpty());
        }

    }


}
