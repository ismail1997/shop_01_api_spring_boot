package com.ismail.my_shop_api.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;


    private String email;


    private String password;


    private String firstName;


    private String lastName;


    private String photos;


    private String address;


    private String postalCode;


    private String country ;


    private String city;

    private Set<String> roles = new HashSet<>();
}
