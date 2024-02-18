package com.ismail.shop.repositories;

import com.ismail.shop.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select c from Category c")
    Page<Category> findAll(Pageable pageable);

    @Query("select c from Category c where c.parent.id is null")
    List<Category> findRootCategories();

    @Query("select c from Category  c where c.parent.id is null")
    public Page<Category> findRootCategories(Pageable pageable);
}
