package com.ismail.shop.controllers;

import com.ismail.shop.dtos.CategoryDTO;
import com.ismail.shop.dtos.CategoryPageDTO;
import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.exceptions.CategoryNotFoundException;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.services.CategoryService;
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
@RequestMapping("/categories")
public class CategoryRestController {
    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories(){
        return this.categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO getOneCategoryByID(@PathVariable("id") Long id) throws CategoryNotFoundException {
        return this.categoryService.getOneCategoryByID(id);
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO){
        return this.categoryService.createCategory(categoryDTO);
    }

    @GetMapping("/page-categories")
    public CategoryPageDTO getPageOfCategories( @RequestParam(name = "page",defaultValue = "0") int page ,
                                                @RequestParam(name = "size", defaultValue = "10") int size){
        return this.categoryService.getPageOfCategories(page,size);
    }

    @GetMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageOfCategory(@PathVariable("id") Long id) throws IOException, CategoryNotFoundException {
        CategoryDTO categoryDTO  = this.categoryService.getOneCategoryByID(id);
        String photoName = categoryDTO.getImage();

        File file = new File(Constants.CATEGORIES_IMAGES +categoryDTO.getId()+"\\"+photoName);

        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }
}
