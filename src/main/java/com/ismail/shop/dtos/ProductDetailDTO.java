package com.ismail.shop.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ismail.shop.entities.Product;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailDTO {

    private Integer id;

    private String name;


    private String value;

    //private ProductDTO product;
}