package com.ismail.shop.brand;


import com.ismail.shop.entities.Brand;
import com.ismail.shop.exceptions.BrandNotFoundException;
import com.ismail.shop.repositories.BrandRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testGetAllBrands(){
        List<Brand> brandList = brandRepository.findAll();
        brandList.forEach(System.out::println);
        assertThat(brandList).size().isGreaterThan(0);
    }

    @Test
    public void tesFindBrandByID()
    {
        long id = 3l;
        Brand brand = brandRepository.findById(id).get();
        assertThat(brand).isNotNull();
    }

    @Test
    public void testThrowException(){
        long id = 35l;
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(()->{
                    Brand brand = brandRepository.findById(id).get();
        });
    }

    @Test
    public void testCreateBrand()
    {
        Brand brand = Brand.builder().name("My Brand Created").logo("The Brand that just created").build();
        Brand savedBrand = brandRepository.save(brand);

        assertThat(savedBrand).isNotNull();
    }

}
