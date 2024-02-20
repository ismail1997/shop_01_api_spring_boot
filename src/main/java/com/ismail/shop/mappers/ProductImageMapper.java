package com.ismail.shop.mappers;


import com.ismail.shop.dtos.ProductImagedDTO;
import com.ismail.shop.entities.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(target = "product", ignore = true)
    ProductImage toEntity(ProductImagedDTO productImagedDTO);

    @Mapping(target = "product", ignore = true)
    ProductImagedDTO toDto(ProductImage productImage);
}
