package com.ismail.shop.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandDTO {
    private Long id;
    private String name ;
    private String logo;
    private List<CategoryDTO> categories ;
}
