package com.ismail.shop.repositories;

import com.ismail.shop.entities.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {

    @Query("select b from Brand b")
    Page<Brand> findAll(Pageable pageable);
}
