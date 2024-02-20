package com.ismail.shop.services;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.dtos.ProductPageDTO;
import com.ismail.shop.exceptions.ProductNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getOneProductByID(Long id) throws ProductNotFoundException;
    ProductDTO createProduct(ProductDTO productDTO);
    void deleteProductByID(Long id);

    ProductPageDTO getPageOfProducts(int page , int size);

    byte[] getImageOfProductByID(Long id) throws ProductNotFoundException, IOException;
}
