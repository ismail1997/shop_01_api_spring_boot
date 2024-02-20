package com.ismail.shop.services;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.dtos.ProductPageDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getOneProductByID(Long id);
    ProductDTO createProduct(ProductDTO productDTO);
    void deleteProductByID(Long id);

    ProductPageDTO getPageOfProducts(int page , int size);
}
