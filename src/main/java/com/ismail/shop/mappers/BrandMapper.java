package com.ismail.shop.mappers;

import com.ismail.shop.dtos.BrandDTO;
import com.ismail.shop.entities.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    //@Mapping(target = "categories", ignore = true)
    Brand fromDTO(BrandDTO brandDTO);

    //@Mapping(target = "categories", ignore = true)
    BrandDTO fromEntity(Brand brand);
}
