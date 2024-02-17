package com.ismail.shop.mappers;


import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
