package com.ismail.shop.controllers;

import com.ismail.shop.dtos.BrandDTO;
import com.ismail.shop.dtos.BrandPageDTO;
import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.dtos.UserPageDTO;
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
            @RequestParam(name = "page",defaultValue = "0") int page ,
            @RequestParam(name = "size", defaultValue = "10") int size ){
        return  this.brandService.getPageOfBrands(page,size);
    }

    @GetMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageOfBrand(@PathVariable("id") Long id) throws BrandNotFoundException, IOException {
        BrandDTO brandDTO  = this.brandService.getOneBrandByID(id);
        String photoName = brandDTO.getLogo();

        File file = new File(Constants.BRANDS_IMAGES +brandDTO.getId()+"\\"+photoName);

        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

}
