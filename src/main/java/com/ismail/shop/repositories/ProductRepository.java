package com.ismail.shop.repositories;

import com.ismail.shop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByName(String name);

    @Query("select p from Product  p")
    Page<Product> findAll(Pageable pageable);


}
