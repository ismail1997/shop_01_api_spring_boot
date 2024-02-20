package com.ismail.shop.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ismail.shop.entities.Brand;
import com.ismail.shop.entities.Category;
import com.ismail.shop.entities.ProductDetail;
import com.ismail.shop.entities.ProductImage;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private Long id;

    private String name;


    private String alias;


    private String shortDescription;


    private String fullDescription;


    private Date createdTime;

    private Date updatedTime;

    private boolean enabled;


    private boolean inStock;

    private float cost;

    private float price;


    private float discountPercent;

    private float length;
    private float width;
    private float height;
    private float weight;


    private String mainImage;


    private CategoryDTO category;


    private BrandDTO brand;


    private List<ProductImagedDTO> images ;


    private List<ProductDetailDTO> details ;


}
