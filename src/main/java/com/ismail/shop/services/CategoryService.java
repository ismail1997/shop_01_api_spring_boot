package com.ismail.shop.services;

import com.ismail.shop.dtos.CategoryDTO;
import com.ismail.shop.dtos.CategoryPageDTO;
import com.ismail.shop.entities.Category;
import com.ismail.shop.exceptions.CategoryNotFoundException;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getOneCategoryByID(Long id) throws CategoryNotFoundException;
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryPageDTO getPageOfCategories(int page, int size);

    byte[] getImageOfCategory(Long id) throws IOException, CategoryNotFoundException;
}
