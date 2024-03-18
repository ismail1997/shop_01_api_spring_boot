package com.ismail.shop.services;

import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.dtos.UserPageDTO;
import com.ismail.shop.exceptions.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getOneUserByID(Long id) throws UserNotFoundException;
    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO) throws UserNotFoundException;

    void deleteUserByID(Long id) throws UserNotFoundException;

    UserPageDTO getPageOfUsers(int page, int size);

    boolean checkIfEmailAlreadyExisted(String email);

    String uploadUserPhoto(Long id, MultipartFile file) throws IOException, UserNotFoundException;

    void changeUserEnabledStatus(Long id, boolean enabled) ;

    byte[] getImageOfUser(Long id) throws UserNotFoundException, IOException;
}
