package com.ismail.shop.controllers;

import com.ismail.shop.dtos.*;
import com.ismail.shop.entities.Brand;
import com.ismail.shop.exceptions.BrandNotFoundException;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.services.BrandService;
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
@RequestMapping("/brands")
public class BrandRestController {
    private BrandService brandService;

    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<BrandDTO> getAllBrands(){
        return this.brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public BrandDTO getOneBrandByID(@PathVariable("id") Long id) throws BrandNotFoundException {
        return this.brandService.getOneBrandByID(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBrandByID(@PathVariable("id") Long id) throws BrandNotFoundException {
        this.brandService.deleteBrandByID(id);
    }

    @PostMapping
    public BrandDTO createBrand(BrandDTO brandDTO){
        return this.brandService.createBrand(brandDTO);
    }


    @GetMapping("/page-brands")
    public BrandPageDTO getPageOfBrands(
            @RequestParam(name = "page",defaultValue = "1") int page ,
            @RequestParam(name = "size", defaultValue = "10") int size ){
        return  this.brandService.getPageOfBrands(page,size);
    }

    @GetMapping("/{id}/categories")
    public List<CategoryDTO> getCategoriesOfBrand(@PathVariable("id") Long id) throws BrandNotFoundException {
        return this.brandService.getCategoriesOfBrand(id);
    }

    @GetMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageOfBrand(@PathVariable("id") Long id) throws BrandNotFoundException, IOException {
       return this.brandService.getImageOfBrand(id);
    }



}
