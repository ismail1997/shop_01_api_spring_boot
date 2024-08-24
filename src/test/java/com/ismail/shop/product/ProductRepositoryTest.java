package com.ismail.shop.product;


import com.ismail.shop.entities.Brand;
import com.ismail.shop.entities.Category;
import com.ismail.shop.entities.Product;
import com.ismail.shop.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = NONE)
@Rollback(value = true)
public class ProductRepositoryTest {
    private final ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    public void getAllProductTest() {
        List<Product> products = productRepository.findAll();
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isGreaterThan(0);
    }

    @Test
    public void findProductByIdTest() {
        long productID = 1l;

        Optional<Product> optionalProduct = productRepository.findById(productID);
        assertThat(optionalProduct).isPresent();

        Product product = optionalProduct.get();
        assertThat(product.getId()).isEqualTo(productID);
        assertThat(product.getName()).isEqualTo("iPhone 14 Pro");
        assertThat(product.getPrice()).isEqualTo(999.00f);// 800.00,
        assertThat(product.getCost()).isEqualTo(800.00f);
        assertThat(product.getAlias()).isEqualTo(product.getName().toLowerCase().replaceAll("\\s", "-"));
        assertTrue(product.isEnabled(), "Should return false the product is enabled");
        assertFalse(product.isInStock(), "Should return false, the product is not in stock");
    }

    @Test
    public void getProductCategoryTest() {
        long productID = 2l;
        Optional<Product> optionalProduct = productRepository.findById(productID);
        assertThat(optionalProduct).isPresent();

        Product product = optionalProduct.get();

        assertThat(product).isNotNull();
        assertThat(product.getCategory()).isNotNull();

        Category category = product.getCategory();

        assertThat(category.getAlias()).isEqualTo("Smartphones".toLowerCase());
    }

    @Test
    public void getProductBrandTest() {
        long productID = 3l;
        Optional<Product> optionalProduct = productRepository.findById(productID);

        assertThat(optionalProduct).isPresent();

        Product product = optionalProduct.get();

        assertThat(product).isNotNull();
        assertThat(product.getBrand()).isNotNull();

        Brand brand = product.getBrand();

        assertThat(brand.getName()).isEqualTo("Dell");
    }


    @Test
    public void createProductTest() {
        Product newProduct = Product.builder()
                .name("Test Product")
                .alias("test-product")
                .shortDescription("This is a short description for Test Product.")
                .fullDescription("This is a full description for Test Product, with more details and information.")
                .createdTime(new Date())
                .updatedTime(new Date())
                .enabled(true)
                .inStock(true)
                .cost(100.00f)
                .price(150.00f)
                .discountPercent(10.0f)
                .length(10.0f)
                .width(5.0f)
                .height(2.0f)
                .weight(1.0f)
                .mainImage("test_product.jpg")
                .category(new Category(1l))
                .brand(new Brand(1l))
                .build();

        Product savedProduct = productRepository.save(newProduct);

        assertThat(savedProduct.getId()).isNotNull();

        Optional<Product> optionalProduct = productRepository.findById(savedProduct.getId());
        assertThat(optionalProduct).isPresent();

        Product retrievedProduct = optionalProduct.get();

        assertThat(retrievedProduct.getName()).isEqualTo("Test Product");
        assertThat(retrievedProduct.getAlias()).isEqualTo("test-product");
        assertThat(retrievedProduct.getShortDescription()).isEqualTo("This is a short description for Test Product.");
        assertThat(retrievedProduct.getFullDescription()).isEqualTo("This is a full description for Test Product, with more details and information.");
        assertThat(retrievedProduct.getCost()).isEqualTo(100.00f);
        assertThat(retrievedProduct.getPrice()).isEqualTo(150.00f);
        assertThat(retrievedProduct.getDiscountPercent()).isEqualTo(10.0f);
        assertThat(retrievedProduct.isEnabled()).isTrue();
        assertThat(retrievedProduct.isInStock()).isTrue();
        assertThat(retrievedProduct.getCategory().getId()).isEqualTo(1L);
        assertThat(retrievedProduct.getBrand().getId()).isEqualTo(1L);
    }

    @Test
    public void updateProductTest()
    {
        long productID = 1l;
        Optional<Product> optionalProduct = productRepository.findById(productID);
        assertThat(optionalProduct).isPresent();

        Product product = optionalProduct.get();
        product.setName("This is the updated product");
        product.setPrice(444.44f);

        Product updatedProduct = productRepository.save(product);

        assertThat(updatedProduct.getPrice()).isEqualTo(444.44f);
        assertThat(updatedProduct.getName()).isEqualTo("This is the updated product");
    }

    @Test
    public void deleteProductTest()
    {
        Product product = Product.builder()
                .name("PRODUCT_TO_DELETE")
                .alias("PRODUCT_TO_DELETE")
                .fullDescription("this is the description ")
                .shortDescription("this is the short description")
                .mainImage("test_product.jpg")
                .price(444.44f)
                .cost(45.00f)
                .build();

        Product savedProduct = productRepository.save(product);
        long idProductToDelete = savedProduct.getId();

        Optional<Product> productOptional = productRepository.findById(idProductToDelete);
        assertThat(productOptional).isPresent();

        productRepository.deleteById(idProductToDelete);

        //verify that the product is deleted
        Optional<Product> deletedProductOptional = productRepository.findById(idProductToDelete);
        assertThat(deletedProductOptional).isEmpty();


    }
}
