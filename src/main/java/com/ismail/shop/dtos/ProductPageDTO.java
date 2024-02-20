package com.ismail.shop.dtos;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductPageDTO {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<ProductDTO> productDTOS;
}
