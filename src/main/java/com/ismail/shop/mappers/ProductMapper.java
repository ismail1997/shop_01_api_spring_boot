package com.ismail.shop.mappers;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    //@Mapping(target = "category", ignore = true)
    //@Mapping(target = "brand", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "details", ignore = true)
    Product toEntity(ProductDTO productDTO);

    //@Mapping(target = "category", ignore = true)
    //@Mapping(target = "brand", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "details", ignore = true)
    ProductDTO toDto(Product product);
}
