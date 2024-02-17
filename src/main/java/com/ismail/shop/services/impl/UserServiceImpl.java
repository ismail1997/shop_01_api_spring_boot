package com.ismail.shop.services.impl;


import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.entities.User;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.mappers.UserMapper;
import com.ismail.shop.repositories.UserRepository;
import com.ismail.shop.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
}
