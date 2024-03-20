package org.example.Controller;

import org.example.Exceptions.SellerException;
import org.example.Model.Seller;
import org.example.Service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {
    SellerService sellerService;
    public SellerController(SellerService sellerService){
        this.sellerService = sellerService;
    }
    @GetMapping(value="/sellers", params = "sellerName")
    public ResponseEntity<List<Seller>> getAllSellersByTitle(@RequestParam String sellerName){
        List<Seller> sellers;
        if (sellerName == null) {
            sellers = sellerService.getAllSellers();
        }else{
            sellers = sellerService.getAllSellersByTitle(sellerName);
        }
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @GetMapping(value = "/sellers")
    public ResponseEntity<List<Seller>> getAllSeller(){
        List<Seller> paintings;
        paintings = sellerService.getAllSellers();
        return new ResponseEntity<>(paintings, HttpStatus.OK);
    }

    @PostMapping("/product/{id}/sellers")
    public ResponseEntity<Seller> addSeller(@RequestBody Seller s, @PathVariable Long id) throws Exception {
        Seller seller = sellerService.saveSeller(id, s);
        return new ResponseEntity<>(seller, HttpStatus.CREATED);
    }

    @GetMapping("/sellers/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable long id){
        try{
            Seller p = sellerService.getById(id);
            return new ResponseEntity<>(p, HttpStatus.OK);
        }catch (SellerException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sellers")
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        try {
            Seller createdSeller = sellerService.createSeller(seller);
            return new ResponseEntity<>(createdSeller, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Handle specific exceptions
        }
    }

    @DeleteMapping("/sellers/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable Long id){
        try{
            sellerService.deleteSeller(id);
            return new ResponseEntity<>("Seller deleted successfully", HttpStatus.OK);
        }
        catch(SellerException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
