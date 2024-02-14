import org.example.Exceptions.SellerException;
import org.example.Model.Seller;
import org.example.Service.SellerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class SellerTesting {

    SellerService sellerService;
    @Before
    public void SellerTesting(){
        sellerService = new SellerService();
    }

    @After
    public void tearDown(){
        sellerService.getSellerList().clear();
    }

    @Test
    public void insertProductTest(){
        String sellerName = "Benny";

        Seller s =new Seller(sellerName);

        try {
            sellerService.postSeller(s);
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

        Seller s = new Seller(sellerName);

        try {
            sellerService.postSeller(s);
            Assert.fail("Expected Seller Exception due to empty product name");
        } catch (SellerException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("You cannot have a blank seller name!", e.getMessage());
            Assert.assertTrue(sellerService.getSellerList().isEmpty());
        }

    }


}
