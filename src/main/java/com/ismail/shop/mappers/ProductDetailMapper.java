package com.ismail.shop.mappers;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.dtos.ProductDetailDTO;
import com.ismail.shop.entities.Product;
import com.ismail.shop.entities.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {

    //@Mapping(target = "product", ignore = true)
    ProductDetail toEntity(ProductDetailDTO productDetailDTO);

    //@Mapping(target = "product", ignore = true)
    ProductDetailDTO toDto(ProductDetail productDetail);
}
