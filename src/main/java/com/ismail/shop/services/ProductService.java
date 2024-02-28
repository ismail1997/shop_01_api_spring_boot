package com.ismail.shop.services;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.dtos.ProductImagedDTO;
import com.ismail.shop.dtos.ProductPageDTO;
import com.ismail.shop.entities.ProductImage;
import com.ismail.shop.exceptions.ProductImageNotFoundException;
import com.ismail.shop.exceptions.ProductNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getOneProductByID(Long id) throws ProductNotFoundException;
    ProductDTO createProduct(ProductDTO productDTO);
    void deleteProductByID(Long id);

    ProductPageDTO getPageOfProducts(int page , int size);

    byte[] getImageOfProductByID(Long id) throws ProductNotFoundException, IOException;

    byte[] getExtraImageOfProduct(Long idProduct, Long idProductImages) throws ProductImageNotFoundException, ProductNotFoundException, IOException;

    String uploadProductMainPhoto(Long id, MultipartFile file) throws IOException, ProductNotFoundException;

    String uploadProductExtrasPhotos(Long id, MultipartFile[] files) throws ProductNotFoundException, IOException;

    List<ProductImagedDTO> getExtrasImagesOfProduct(Long id) throws ProductNotFoundException;
}
