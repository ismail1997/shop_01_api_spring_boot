package com.ismail.shop.category;


import com.ismail.shop.entities.Brand;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
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
        Category category = Category.builder().name("spring").alias("spring").image("spring.png").enabled(true).build();
        Category parent = categoryRepository.findById(1l).get();
        category.setParent(parent);

        Category savedCategory = categoryRepository.save(category);
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("spring");
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
    public void testDelete(){
        long id= 10l;
        categoryRepository.deleteById(id);

        Optional<Category> result = categoryRepository.findById(id);
        assertThat(result).isEmpty();
    }
}
