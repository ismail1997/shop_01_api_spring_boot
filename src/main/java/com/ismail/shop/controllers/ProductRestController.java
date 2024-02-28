package com.ismail.shop.controllers;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.dtos.ProductImagedDTO;
import com.ismail.shop.dtos.ProductPageDTO;
import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.entities.ProductImage;
import com.ismail.shop.exceptions.ProductImageNotFoundException;
import com.ismail.shop.exceptions.ProductNotFoundException;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.services.ProductService;
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


    @PostMapping(value="/{id}/upload-main-image", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadMainImageOfProduct(@PathVariable("id") Long id, @RequestParam("image") MultipartFile file)    {
        try {
            String imageUrl = service.uploadProductMainPhoto(id, file);
            return ResponseEntity.ok().body("{\"imageUrl\": \"" + imageUrl + "\"}");
        } catch (IOException | ProductNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while uploading the image.\"}");
        }
    }

    @PostMapping(value = "/{id}/upload-extras-images",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProductExtrasImages(@PathVariable("id") Long id,  @RequestPart("files") MultipartFile[] files) {
        try {
            String status = service.uploadProductExtrasPhotos(id, files);
            return ResponseEntity.ok().body("{\"imageUrl\": \"" + status + "\"}");
        } catch (IOException | ProductNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while uploading the image.\"}");
        }
    }

    @GetMapping("/{id}/extras-images")
    public ResponseEntity<List<ProductImagedDTO>> getExtrasImagesOfProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        return ResponseEntity.ok().body(this.service.getExtrasImagesOfProduct(id));
    }


    @GetMapping(value="/{id}/extras-images/{idImage}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getExtraImageOfProductByID(@PathVariable("id") Long idProduct,@PathVariable("idImage") Long idProductImage){
        try{
            return ResponseEntity.ok().body(this.service.getExtraImageOfProduct(idProduct,idProductImage));
        }catch(ProductImageNotFoundException | ProductNotFoundException | IOException exception){
           exception.printStackTrace();
           return null; //todo return default image if there is any error
        }
    }



}
