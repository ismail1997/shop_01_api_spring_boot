package com.ismail.shop.services.impl;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.dtos.ProductPageDTO;
import com.ismail.shop.services.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public List<ProductDTO> getAllProducts() {
        return null;
    }

    @Override
    public ProductDTO getOneProductByID(Long id) {
        return null;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public void deleteProductByID(Long id) {

    }

    @Override
    public ProductPageDTO getPageOfProducts(int page, int size) {
        return null;
    }
}
