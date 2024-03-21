package org.example.Repository;

import org.example.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findBySellerName(String sellerName);
    //JPQL query (java persistence query language)
//    SQL dialect agnostic
//    directly maps to an ORM entity
//    (we can still use native sql queries / named queries if we choose)
    @Query("from Seller where sellerName=:sellerName")
    List<Seller> findBySellerName2(@Param("sellerName") String sellerName);

}
