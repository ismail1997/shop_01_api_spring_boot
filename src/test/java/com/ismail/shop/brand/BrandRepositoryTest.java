package com.ismail.shop.brand;


import com.ismail.shop.entities.Brand;
import com.ismail.shop.entities.Category;
import com.ismail.shop.repositories.BrandRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
public class BrandRepositoryTest {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandRepositoryTest(BrandRepository brandRepository){
        this.brandRepository=brandRepository;
    }

    @Test
    public void testGetAllBrands(){
        List<Brand> brandList = brandRepository.findAll();
        brandList.forEach(System.out::println);
        assertThat(brandList).size().isGreaterThan(0);
    }

    @Test
    public void tesFindBrandByID()
    {
        long id = 10l;
        Brand brand = brandRepository.findById(id).get();
        assertThat(brand).isNotNull();
        assertThat(brand.getName()).isEqualTo("Huawei");
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
        int randomSuffix = new Random().nextInt(50);
        String brandName = "BrandForTest"+randomSuffix;

        Brand brand = Brand.builder()
                .name(brandName)
                .logo("The Brand that just created")
                .build();

        Brand savedBrand = brandRepository.save(brand);

        assertThat(savedBrand).isNotNull();
    }


    @Test
    public void testCreateBrandWithCategories(){
        Category category = new Category();
        category.setId(1l);

        int randomSuffix = new Random().nextInt(40);
        String brandName = "BrandWithCategoryForTest"+randomSuffix;

        Brand brand = Brand
                .builder()
                .name(brandName)
                .logo(brandName+"logo")
                .build();

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        brand.setCategories(categories);

        Brand savedBrand = brandRepository.save(brand);

        assertThat(savedBrand).isNotNull();
    }


    @Test
    public void testUpdateName(){
        String newName = "Samsung Electronics";
        Brand samsung = brandRepository.findById(2l).get();
        samsung.setName(newName);

        Brand savedBrand= brandRepository.save(samsung);
        assertThat(savedBrand.getName()).isEqualTo(newName);
    }

    @Test
    public void testDelete(){

        // Step 1: Create a new category to ensure there's something to delete
        Brand brand= Brand
                .builder()
                .name("BrandToDelete")
                .logo("BrandLogoToDelete")
                .build();

        Brand savedBrand = brandRepository.save(brand);
        long idToDelete = savedBrand.getId();

        // Step 2: Ensure the brand exists before deletion
        Optional<Brand> brandToDeleteOptional = brandRepository.findById(idToDelete);
        assertThat(brandToDeleteOptional).isPresent();//to verify it exists


        brandRepository.deleteById(idToDelete);

        Optional<Brand> result = brandRepository.findById(idToDelete);
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetCategoriesOfBrand(){
        Optional<Brand> optionalBrand = brandRepository.findById(1l);
        Brand brand = optionalBrand.get();

        brand.getCategories().forEach(System.out::println);

        assertThat(brand.getCategories().size()).isGreaterThan(0);
    }
}
