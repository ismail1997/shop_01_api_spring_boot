package com.ismail.shop.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductImagedDTO {

    private Long id;


    private String name;



    //private ProductDTO product;
}
