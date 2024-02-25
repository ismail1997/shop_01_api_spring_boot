package com.ismail.shop.mappers;

import com.ismail.shop.dtos.CategoryDTO;
import com.ismail.shop.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    //@Mapping(target = "parent", ignore = true)
    //@Mapping(target = "children", ignore = true)
    Category fromDTO(CategoryDTO categoryDTO);

    //@Mapping(target = "parent", ignore = true)
    //@Mapping(target = "children", ignore = true)
    CategoryDTO fromEntity(Category category);
}
