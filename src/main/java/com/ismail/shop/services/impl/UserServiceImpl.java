package com.ismail.shop.services.impl;


import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.dtos.UserPageDTO;
import com.ismail.shop.entities.User;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.mappers.UserMapper;
import com.ismail.shop.repositories.UserRepository;
import com.ismail.shop.services.UserService;
import com.ismail.shop.utilities.Constants;
import com.ismail.shop.utilities.FileUploadUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(user -> {
            return this.userMapper.toDTO(user);
        }).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public UserDTO getOneUserByID(Long id) throws UserNotFoundException {
        User user = this.userRepository.findById(id).orElseThrow(()->new UserNotFoundException(String.format("Can not find user with this id %d ",id)));
        UserDTO userDTO = this.userMapper.toDTO(user);
        return userDTO;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.userMapper.toEntity(userDTO);
        User savedUser = this.userRepository.save(user);
        return this.userMapper.toDTO(savedUser);
    }

    @Override
    public void deleteUserByID(Long id) throws UserNotFoundException {
        User user = this.userRepository.findById(id).orElseThrow(()->new UserNotFoundException(String.format("Can not find user with this id %d ",id)));
        this.userRepository.deleteById(id);
    }

    @Override
    public UserPageDTO getPageOfUsers(int page , int size){
        if(page< 0) page = 0;
        if(size<0) size = 10;

        Page<User> userPage = this.userRepository.findAll(PageRequest.of(page, size));

        List<UserDTO> userDTOS = userPage.getContent().stream().map(user -> {
            return this.userMapper.toDTO(user);
        }).collect(Collectors.toList());

        UserPageDTO userPageDTO = new UserPageDTO();
        userPageDTO.setUserDTOS(userDTOS);
        userPageDTO.setPageSize(size);
        userPageDTO.setCurrentPage(page);
        userPageDTO.setTotalPages(userPage.getTotalPages());


        return userPageDTO;
    }

    @Override
    public boolean checkIfEmailAlreadyExisted(String email){
        User user = this.userRepository.findByEmail(email.toLowerCase().trim());
        return user != null;
    }

    @Override
    public String uploadUserPhoto(Long id, MultipartFile file) throws IOException, UserNotFoundException {
        UserDTO user = getOneUserByID(id);
        if(!file.isEmpty()){
           String fileName= StringUtils.cleanPath(file.getOriginalFilename());
           String uploadDir = Constants.USERS_IMAGES+id;

           user.setPhotos(fileName);
           this.userRepository.save(this.userMapper.toEntity(user));

           //first we clean the directory to avoid duplicate images
           FileUploadUtil.cleanDir(uploadDir);
           FileUploadUtil.saveFile(uploadDir,fileName,file);
           return fileName;
       }
        return "image-not-uploaded";
    }


    @Override
    public void changeUserEnabledStatus(Long id, boolean enabled)  {
        this.userRepository.updateUserEnabledStatus(id,enabled);
    }

}
