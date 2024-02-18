package com.ismail.shop;

import com.ismail.shop.entities.Category;
import com.ismail.shop.entities.Role;
import com.ismail.shop.entities.User;
import com.ismail.shop.repositories.CategoryRepository;
import com.ismail.shop.repositories.RoleRepository;
import com.ismail.shop.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class MyShopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyShopApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository, UserRepository userRepository){
        return args -> {
            Stream.of("ADMIN","EDITOR","CUSTOMER","SHIPPER").forEach(role->{
                Role r = new Role();
                r.setName(role);
                r.setDescription(role);
                roleRepository.save(r);
            });


            List<Role> roles = roleRepository.findAll();

            Stream.of("ismail","mardona","jsohn","vinisduis","bouadsdi","johfn","vinisuis","bouaddi",
                    "john","michael","messi","ronaldo","modric","zidan").forEach(u->{
                User user = User.builder()
                        .firstName(u)
                        .lastName(u)
                        .email(u+"@gmail.com")
                        .country("France")
                        .city("Paris")
                        .address("Paris Rue 2 ")
                        .password(u+"12345")
                        .build();

                if(u.startsWith("j")) user.setEnabled(false);
                else user.setEnabled(true);


                int randomNumber = new Random().nextInt(roles.size());
                List<Role> roleList = new ArrayList<>();
                for(int i =0 ; i<randomNumber; i++){
                    roleList.add(roles.get(i));
                }
                user.setRoles(roleList);

                userRepository.save(user);
            });



        };
    }


    @Bean
    public CommandLineRunner categoryCommandLineRunner(CategoryRepository categoryRepository){
        return args -> {
            Stream.of("Computers","SmartPhones","Printers","Electricity","Cosmetic","Electronics").forEach(c->{
                Category category = new Category();
                category.setAlias(c);
                category.setEnabled(true);
                category.setName(c);
                category.setImage(c+".png");

                categoryRepository.save(category);
            });
        };
    }

}
