package com.ismail.shop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 256, nullable = false)
    private String name;

    @Column(unique = true,length = 256, nullable = false)
    private String alias;

    @Column(length = 512, nullable = false,name = "short_description")
    private String shortDescription;

    @Column(length = 4096, nullable = false,name = "full_description")
    private String fullDescription;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    private boolean enabled;

    @Column(name = "in_stock")
    private boolean inStock;

    private float cost;

    private float price;

    @Column(name = "discount_percent")
    private float discountPercent;

    private float length;
    private float width;
    private float height;
    private float weight;

    @Column(name = "main_image",nullable = false)
    private String mainImage;

    @ManyToOne
    @JoinColumn(
            name="category_id"
    )
    private Category category;

    @ManyToOne
    @JoinColumn(
            name="brand_id"
    )
    private Brand brand;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images ;

    @OneToMany(mappedBy ="product",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetail> details ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return enabled == product.enabled && inStock == product.inStock && Float.compare(product.cost, cost) == 0 && Float.compare(product.price, price) == 0 && Float.compare(product.discountPercent, discountPercent) == 0 && Float.compare(product.length, length) == 0 && Float.compare(product.width, width) == 0 && Float.compare(product.height, height) == 0 && Float.compare(product.weight, weight) == 0 && id.equals(product.id) && name.equals(product.name) && alias.equals(product.alias) && shortDescription.equals(product.shortDescription) && fullDescription.equals(product.fullDescription) && createdTime.equals(product.createdTime) && updatedTime.equals(product.updatedTime) && mainImage.equals(product.mainImage) && category.equals(product.category) && brand.equals(product.brand) && images.equals(product.images) && details.equals(product.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, alias, shortDescription, fullDescription, createdTime, updatedTime, enabled, inStock, cost, price, discountPercent, length, width, height, weight, mainImage, category, brand, images, details);
    }

    public void addDetail(ProductDetail detail){
        this.details.add(detail);
    }
}
