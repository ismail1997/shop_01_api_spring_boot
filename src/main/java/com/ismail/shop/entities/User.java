package com.ismail.shop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128,nullable = false,unique = true)
    private String email;

    @Column(length = 64,nullable = false)
    private String password;

    @Column(name = "first_name",length = 45,nullable = false)
    private String firstName;

    @Column(name="last_name",length = 54,nullable = false)
    private String lastName;

    @Column(length = 64)
    private String photos;

    private boolean enabled;
    @Column(length = 255)
    private String address;

    @Column(length = 64)
    private String postalCode;

    @Column(length = 64)
    private String country ;

    @Column(length = 64)
    private String city;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles ;
}
