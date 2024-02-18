package com.ismail.shop.services;

import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.dtos.UserPageDTO;
import com.ismail.shop.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getOneUserByID(Long id) throws UserNotFoundException;
    UserDTO createUser(UserDTO userDTO);
    void deleteUserByID(Long id) throws UserNotFoundException;

    UserPageDTO getPageOfUsers(int page, int size);
}
