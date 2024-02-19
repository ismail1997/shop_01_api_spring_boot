package com.ismail.shop.controllers;


import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.dtos.UserPageDTO;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.services.UserService;
import com.ismail.shop.utilities.Constants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
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

    @GetMapping("/page-users")
    public UserPageDTO getPageOfUsers(
            @RequestParam(name = "page",defaultValue = "0") int page ,
            @RequestParam(name = "size", defaultValue = "10") int size ){
        return  this.userService.getPageOfUsers(page,size);
    }

    @GetMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageOfUser(@PathVariable("id") Long id) throws UserNotFoundException, IOException {
        UserDTO userDTO  = this.userService.getOneUserByID(id);
        String photoName = userDTO.getPhotos();

        File file = new File(Constants.USERS_IMAGES +userDTO.getId()+"\\"+photoName);

        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @GetMapping("/check-email-uniqueness/{email}")
    public  boolean checkIfEmailAlreadyExisted(@PathVariable("email") String email){
        return this.userService.checkIfEmailAlreadyExisted(email);
    }

}
