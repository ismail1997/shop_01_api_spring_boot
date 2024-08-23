package com.ismail.shop.category;


import com.ismail.shop.entities.Category;
import com.ismail.shop.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
public class CategoryRepositoryTest {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryRepositoryTest(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Test
    public void testGetAllCategories(){
        List<Category> categories = this.categoryRepository.findAll();
        assertThat(categories).size().isGreaterThan(0);
        categories.forEach(System.out::println);
    }

    @Test
    public void testGetCategoryByID(){
        Optional<Category> optionalCategory = this.categoryRepository.findById(7l);
        assertThat(optionalCategory.isPresent());
        assertThat(optionalCategory.get().getName()).isEqualTo("Accessories");
    }

    @Test
    public void testThrowException(){
        long id = 35l;
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(()->{
                    Category category = categoryRepository.findById(id).get();
                });
    }

    @Test
    public void testCreateCategory(){
        // Generate a random number for unique category name and alias
        int randomSuffix = new Random().nextInt(200);
        String categoryName = "spring" + randomSuffix;

        // Build the new Category object
        Category category = Category.builder()
                .name(categoryName)
                .alias(categoryName)
                .image("spring.png")
                .enabled(true)
                .build();

        // Retrieve the parent category, assuming ID 1 exists
        Optional<Category> parentCategoryOptional = categoryRepository.findById(1L);
        assertThat(parentCategoryOptional).isPresent(); // Ensure parent category is found

        Category parentCategory = parentCategoryOptional.get();
        category.setParent(parentCategory);

        // Save the new category
        Category savedCategory = categoryRepository.save(category);

        // Verify that the saved category is not null and has the expected name
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo(categoryName);
    }

    @Test
    public void testUpdateCategory(){
        Category category = categoryRepository.findById(2l).get();
        category.setName("updated");
        category.setAlias("updated");
        Category saved = categoryRepository.save(category);

        assertThat(saved.getName()).isEqualTo("updated");
        assertThat(saved.getAlias()).isEqualTo("updated");
    }

    @Test
    public void testDelete() {
        // Step 1: Create a new category to ensure there's something to delete
        Category category = new Category();
        category.setName("TestDeleteCategory");
        category.setAlias("TestDeleteAlias");
        category.setImage("test.png");
        category.setEnabled(true);

        Category savedCategory = categoryRepository.save(category);
        Long idToDelete = savedCategory.getId();

        // Step 2: Ensure the category exists before deletion
        Optional<Category> categoryOptional = categoryRepository.findById(idToDelete);
        assertThat(categoryOptional).isPresent(); // Verify it exists

        // Step 3: Delete the category
        categoryRepository.deleteById(idToDelete);

        // Step 4: Verify the category has been deleted
        Optional<Category> result = categoryRepository.findById(idToDelete);
        assertThat(result).isEmpty();
    }
}
