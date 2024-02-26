package com.ismail.shop;

import com.ismail.shop.entities.*;
import com.ismail.shop.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.stream.Stream;

@SpringBootApplication
public class MyShopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyShopApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository, UserRepository userRepository){
        return args -> {
            Stream.of("ADMIN","EDITOR","CUSTOMER","SHIPPER","ASSISTANT").forEach(role->{
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
                        .photos("1")
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
    public CommandLineRunner categoryCommandLineRunner(CategoryRepository categoryRepository,
                                                       BrandRepository brandRepository,ProductRepository repository){
        return args -> {
            Stream.of("Computers",
                    "Smartphones",
                    "Televisions",
                    "Furniture",
                    "Clothing",
                    "Shoes",
                    "Accessories",
                    "Home Appliances",
                    "Sports Equipment",
                    "Books",
                    "Toys",
                    "Cosmetics",
                    "Jewelry",
                    "Automotive Parts",
                    "Gardening Tools").forEach(c->{
                Category category = new Category();
                category.setAlias(c);
                category.setEnabled(true);
                category.setName(c);
                category.setImage(c+".png");

                categoryRepository.save(category);


            });


            List<Category> categories = categoryRepository.findAll();

            Stream.of(
                    "Apple", "Samsung", "Google", "Microsoft", "Sony",
                    "Nike", "Adidas", "Puma", "Dell", "Huawei",
                    "Xiaomi", "Intel", "Canon", "ASUS", "AMD",
                    "Toyota", "Honda", "Ford", "BMW", "Mercedes-Benz"
            ).forEach(brandName->{

                List<Category> cats = new ArrayList<>();
                for(int i= 0 ; i<3 ;i++) cats.add(categories.get(new Random().nextInt(categories.size())));

                Brand brand  = Brand
                        .builder()
                        .name(brandName)
                        .logo(brandName.toLowerCase()+".png")
                        .categories(cats)
                        .build();
                brandRepository.save(brand);
            });





            Stream.of(
                    "Samsung X23","Apple iPhone 13","Dell Inspiron 15","Sony WH-1000XM4","Fitbit Versa 3",
                    "Canon EOS Rebel T7i","HP OfficeJet Pro 9025", "Logitech G Pro X","Razer DeathAdder Elite",
                    "LG UltraGear 27GL850","Seagate Backup Plus Slim","Linksys AC2200","PlayStation 5",
                    "Garmin Forerunner 245", "DJI Mavic Air 2","Anker PowerCore 10000","JBL Flip 5",
                    "Apple AirPods Pro","Epson Home Cinema 2150", "Google Nest Hub"
            ).forEach(productName->{
                Product product = Product
                        .builder()
                        .name(productName)
                        .alias(productName)

                        .createdTime(new Date())
                        .updatedTime(new Date())
                        .cost(3.0f)
                        .price(3.0f)
                        .enabled(true)
                        .inStock(true)
                        .brand(brandRepository.findById(1l).get())
                        .category(categoryRepository.findById(1l).get())
                        .mainImage(productName+".png")
                        .discountPercent(3.9f)
                        .shortDescription("This is the short description of "+productName)
                        .fullDescription("This is the full description of "+productName)
                        .build();




                repository.save(product);
            });

            repository.findAll().forEach(product -> {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setName("Detail 1 "+product.getName());
                productDetail.setValue("Value 1 "+product.getAlias());
                productDetail.setProduct(product);

                ProductDetail productDetail1 = ProductDetail.builder()
                        .product(product)
                        .name("Detail 2 "+product.getName())
                        .value("Value 2 "+product.getAlias())
                        .build();

                List<ProductDetail> details = new ArrayList<>();
                details.add(productDetail);details.add(productDetail1);

                product.setDetails(details);
                repository.save(product);

            });
        };
    }


}
