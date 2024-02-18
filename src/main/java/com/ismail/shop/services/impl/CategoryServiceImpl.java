package com.ismail.shop.services.impl;

import com.ismail.shop.dtos.CategoryDTO;
import com.ismail.shop.entities.Category;
import com.ismail.shop.exceptions.CategoryNotFoundException;
import com.ismail.shop.mappers.CategoryMapper;
import com.ismail.shop.repositories.CategoryRepository;
import com.ismail.shop.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categoryDTOS = this.categoryRepository.findAll().stream().map(category -> {
            return this.mapper.fromEntity(category);
        }).collect(Collectors.toList());
        return categoryDTOS;
    }

    @Override
    public CategoryDTO getOneCategoryByID(Long id) throws CategoryNotFoundException {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Could not find any category with that id " + id));
        return this.mapper.fromEntity(category);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.mapper.fromDTO(categoryDTO);
        Category savedCategory = this.categoryRepository.save(category);
        return this.mapper.fromEntity(savedCategory);
    }
}