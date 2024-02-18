package com.ismail.shop.dtos;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    private Long id;

    private String name;


    private String alias;

    private String image;

    private boolean enabled;

    private CategoryDTO parent;


    private List<CategoryDTO> children;
}
