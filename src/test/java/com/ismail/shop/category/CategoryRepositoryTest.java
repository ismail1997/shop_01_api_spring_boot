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

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testGetAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).isNotEmpty(); // Check if the list is not empty
    }

    @Test
    public void testGetCategoryById() {
        // Assumes category with ID 1 exists in the test database
        Optional<Category> optionalCategory = categoryRepository.findById(1L);
        assertThat(optionalCategory).isPresent();
        assertThat(optionalCategory.get().getName()).isEqualTo("Electronics");
    }

    @Test
    public void testThrowExceptionWhenCategoryNotFound() {
        long nonExistentId = 999L; // Use an ID that does not exist
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> categoryRepository.findById(nonExistentId).orElseThrow());
    }

    @Test
    public void testCreateCategory() {
        // Generate a random number for unique category name and alias
        int randomSuffix = new Random().nextInt(200);
        String categoryName = "Spring" + randomSuffix;

        // Build the new Category object
        Category category = Category.builder()
                .name(categoryName)
                .alias(categoryName.toLowerCase())
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
        assertThat(savedCategory.getAlias()).isEqualTo(categoryName.toLowerCase());
    }

    @Test
    public void testUpdateCategory() {
        // Assumes category with ID 2 exists in the test database
        Category category = categoryRepository.findById(2L)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        category.setName("Updated Category");
        category.setAlias("updated-category");
        Category updatedCategory = categoryRepository.save(category);

        assertThat(updatedCategory.getName()).isEqualTo("Updated Category");
        assertThat(updatedCategory.getAlias()).isEqualTo("updated-category");
    }

    @Test
    public void testDeleteCategory() {
        // Step 1: Create a new category to ensure there's something to delete
        Category category = Category.builder()
                .name("TestDeleteCategory")
                .alias("test-delete-category")
                .image("delete.png")
                .enabled(true)
                .build();

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
