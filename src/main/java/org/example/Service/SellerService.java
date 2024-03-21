package org.example.Service;

import org.example.Exceptions.ProductException;
import org.example.Model.Seller;
import org.example.Model.Product;
import org.example.Exceptions.SellerException;
import org.example.Repository.SellerRepository;
import org.example.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    ProductRepository productRepository;
    SellerRepository sellerRepository;
    @Autowired
    public SellerService(ProductRepository productRepository, SellerRepository sellerRepository){
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<Seller> getAllSellers(){
        return sellerRepository.findAll();
    }

    public Seller saveSeller(Long productId, Seller seller) throws ProductException, SellerException {

        // Check if a seller with the same name already exists
        Optional<Seller> existingSellerOptional = sellerRepository.findBySellerName(seller.getSellerName());
        if (existingSellerOptional.isPresent()) {
            Seller existingSeller = existingSellerOptional.get();
            if (existingSeller.equals(seller)) {
                throw new SellerException("Seller with the same details already exists");
            }
            throw new SellerException("Seller with the same name already exists");
        }

        // Find the product by ID
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            // Save the seller and associate it with the product
            Seller savedSeller = sellerRepository.save(seller);
            product.getSellers().add(savedSeller);
            productRepository.save(product);
            return savedSeller;
        } else {
            throw new ProductException("No product found with the given id: " + productId);
        }
    }


    public Seller createSeller(Seller s){
        Seller savedSeller = sellerRepository.save(s);
        return savedSeller;
    }

    public void deleteSeller(Long id) throws SellerException {

        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        if(sellerOptional.isPresent()){
            Seller seller = sellerOptional.get();
            sellerRepository.delete(seller);
        }
        else{
            throw new SellerException("Cannot find seller with id: "+ id);
        }


    }


    public List<Seller> getAllSellersByName(String sellerName) {
        return sellerRepository.findBySellerName2(sellerName);
    }

    public Seller getById(Long id) throws SellerException {
        Optional<Seller> p = sellerRepository.findById(id);
        if(p.isEmpty()){
            throw new SellerException("no such seller... ");
        }else{
            return p.get();
        }
    }
}
