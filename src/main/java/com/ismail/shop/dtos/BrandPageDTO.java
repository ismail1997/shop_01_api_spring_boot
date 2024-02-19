package com.ismail.shop.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandPageDTO {
    private int currentPage;
    private int totalPages;
    private int pageSize;

    private List<BrandDTO> brandDTOS;
}
