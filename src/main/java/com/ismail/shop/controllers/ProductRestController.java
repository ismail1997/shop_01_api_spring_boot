package com.ismail.shop.controllers;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.dtos.ProductPageDTO;
import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.exceptions.ProductNotFoundException;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.services.ProductService;
import com.ismail.shop.utilities.Constants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {
    private ProductService service;

    public ProductRestController(ProductService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ProductDTO> getAllProduct(){
        return this.service.getAllProducts();
    }

    @GetMapping("/page-products")
    public ProductPageDTO getPageOfProducts( @RequestParam(name = "page",defaultValue = "0") int page ,
                                             @RequestParam(name = "size", defaultValue = "10") int size){
        return this.service.getPageOfProducts(page,size);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        return this.service.createProduct(productDTO);
    }

    @GetMapping("/{id}")
    public ProductDTO getOneProductByID(@PathVariable("id") Long id) throws ProductNotFoundException {
        return this.service.getOneProductByID(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductWithID(@PathVariable("id") Long id){
        this.service.deleteProductByID(id);
    }

    @GetMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageOfProduct(@PathVariable("id") Long id) throws ProductNotFoundException, IOException {
        return this.service.getImageOfProductByID(id);
    }


}
