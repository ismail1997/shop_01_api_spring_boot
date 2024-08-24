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

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testGetAllBrands() {
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).size().isGreaterThan(0);
        brandList.forEach(System.out::println);
    }

    @Test
    public void testFindBrandByID() {
        long id = 1L; // Adjusted to match an ID that exists in data.sql
        Brand brand = brandRepository.findById(id).get();
        assertThat(brand).isNotNull();
        assertThat(brand.getName()).isEqualTo("Apple");
    }

    @Test
    public void testThrowException() {
        long id = 35L; // An ID that does not exist in data.sql
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {
                    Brand brand = brandRepository.findById(id).get();
                });
    }

    @Test
    public void testCreateBrand() {
        int randomSuffix = new Random().nextInt(50);
        String brandName = "BrandForTest" + randomSuffix;

        Brand brand = Brand.builder()
                .name(brandName)
                .logo("The Brand that just created")
                .build();

        Brand savedBrand = brandRepository.save(brand);

        assertThat(savedBrand).isNotNull();
    }

    @Test
    public void testCreateBrandWithCategories() {
        Category category = new Category();
        category.setId(1L); // Existing category ID

        int randomSuffix = new Random().nextInt(40);
        String brandName = "BrandWithCategoryForTest" + randomSuffix;

        Brand brand = Brand
                .builder()
                .name(brandName)
                .logo(brandName + "logo")
                .build();

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        brand.setCategories(categories);

        Brand savedBrand = brandRepository.save(brand);

        assertThat(savedBrand).isNotNull();
    }

    @Test
    public void testUpdateName() {
        String newName = "Samsung Electronics";
        Brand samsung = brandRepository.findById(2L).get(); // Adjust to match ID in data.sql
        samsung.setName(newName);

        Brand savedBrand = brandRepository.save(samsung);
        assertThat(savedBrand.getName()).isEqualTo(newName);
    }

    @Test
    public void testDelete() {
        // Step 1: Create a new brand to ensure there's something to delete
        Brand brand = Brand
                .builder()
                .name("BrandToDelete")
                .logo("BrandLogoToDelete")
                .build();

        Brand savedBrand = brandRepository.save(brand);
        long idToDelete = savedBrand.getId();

        // Step 2: Ensure the brand exists before deletion
        Optional<Brand> brandToDeleteOptional = brandRepository.findById(idToDelete);
        assertThat(brandToDeleteOptional).isPresent();

        brandRepository.deleteById(idToDelete);

        Optional<Brand> result = brandRepository.findById(idToDelete);
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetCategoriesOfBrand() {
        Optional<Brand> optionalBrand = brandRepository.findById(1L); // Adjust to match ID in data.sql
        Brand brand = optionalBrand.get();

        brand.getCategories().forEach(System.out::println);

        assertThat(brand.getCategories().size()).isGreaterThan(0);
    }
}
