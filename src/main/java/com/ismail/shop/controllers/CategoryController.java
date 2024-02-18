package com.ismail.shop.controllers;

import com.ismail.shop.dtos.CategoryDTO;
import com.ismail.shop.exceptions.CategoryNotFoundException;
import com.ismail.shop.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
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
}
