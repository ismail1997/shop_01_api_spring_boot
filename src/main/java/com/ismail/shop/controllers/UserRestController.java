package com.ismail.shop.controllers;


import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.dtos.UserPageDTO;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.services.UserService;
import com.ismail.shop.utilities.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable("id") Long id,@RequestBody UserDTO userDTO) throws UserNotFoundException {
        return this.userService.updateUser(id,userDTO);
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
       return this.userService.getImageOfUser(id);
    }

    @GetMapping("/check-email-uniqueness/{email}")
    public  boolean checkIfEmailAlreadyExisted(@PathVariable("email") String email){
        return this.userService.checkIfEmailAlreadyExisted(email);
    }

    @PostMapping(value="/{id}/upload-image", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadImageOfUser(@PathVariable("id") Long id, @RequestParam("image") MultipartFile file) throws  UserNotFoundException {
        try {
            String imageUrl = userService.uploadUserPhoto(id, file);
            return ResponseEntity.ok().body("{\"imageUrl\": \"" + imageUrl + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while uploading the image.\"}");
        }
    }

    @PutMapping("/{id}/change-enabled-status")
    public ResponseEntity<?> updateEnabledUser(@PathVariable("id") Long id,@RequestParam(name = "enabled") Boolean enabled){
        boolean isEnabled = enabled != null ? enabled : false;
        this.userService.changeUserEnabledStatus(id,isEnabled);
        return ResponseEntity.ok().body("{\"updated\": \"" + isEnabled + "\"}");
    }


}
