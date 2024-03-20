package org.example.Service;

import org.example.Exceptions.ProductException;
import org.example.Exceptions.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product saveProduct(Product p){
        return productRepository.save(p);
    }

    public void deleteProduct(Long id) throws ProductException {

        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            productRepository.delete(product);
        }
        else{
            throw new ProductException("Cannot find seller with id: "+ id);
        }


    }
}
