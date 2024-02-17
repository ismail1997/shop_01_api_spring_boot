package com.ismail.shop.controllers;


import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public List<UserDTO> getUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getOneUserByID(@PathVariable("id") Long id) throws UserNotFoundException {
        return this.userService.getOneUserByID(id);
    }

    @PostMapping("")
    public UserDTO createUser(@RequestBody UserDTO userDTO)
    {
        return this.userService.createUser(userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        this.userService.deleteUserByID(id);
    }
}
