package com.ismail.shop.mappers;


import com.ismail.shop.dtos.RoleDTO;
import com.ismail.shop.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDTO roleDTO);
    RoleDTO toDTO(Role role);
}
