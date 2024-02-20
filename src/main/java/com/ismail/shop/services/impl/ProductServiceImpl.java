package com.ismail.shop.services.impl;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.dtos.ProductPageDTO;
import com.ismail.shop.entities.Product;
import com.ismail.shop.exceptions.ProductNotFoundException;
import com.ismail.shop.mappers.*;
import com.ismail.shop.repositories.ProductRepository;
import com.ismail.shop.services.ProductService;
import com.ismail.shop.utilities.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private ProductImageMapper productImageMapper;
    private ProductDetailMapper productDetailMapper;
    private CategoryMapper categoryMapper;
    private BrandMapper brandMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductImageMapper productImageMapper, ProductDetailMapper productDetailMapper, CategoryMapper categoryMapper, BrandMapper brandMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productImageMapper = productImageMapper;
        this.productDetailMapper = productDetailMapper;
        this.categoryMapper = categoryMapper;
        this.brandMapper = brandMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDTOS = this.productRepository.findAll().stream().map(product -> {
            return this.productMapper.toDto(product);
        }).collect(Collectors.toList());
        return productDTOS;
    }

    @Override
    public ProductDTO getOneProductByID(Long id) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Could not find any product with id: " + id));
        return this.productMapper.toDto(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = this.productMapper.toEntity(productDTO);

        Product savedProduct = this.productRepository.save(product);
        return this.productMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProductByID(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public ProductPageDTO getPageOfProducts(int page, int size) {
        if(page< 0) page = 0;
        if(size<0) size = 10;

        Page<Product> productPage = this.productRepository.findAll(PageRequest.of(page,size));

        List<ProductDTO> productDTOS = productPage.getContent().stream().map(product -> this.productMapper.toDto(product)).collect(Collectors.toList());

        ProductPageDTO productPageDTO = new ProductPageDTO();
        productPageDTO.setProductDTOS(productDTOS);
        productPageDTO.setPageSize(size);
        productPageDTO.setCurrentPage(page);
        productPageDTO.setTotalPages(productPage.getTotalPages());

        return productPageDTO;
    }


    @Override
    public byte[] getImageOfProductByID(Long id) throws ProductNotFoundException, IOException {
        ProductDTO productDTO  = getOneProductByID(id);
        String photoName = productDTO.getMainImage();
        File file = new File(Constants.PRODUCTS_IMAGES +productDTO.getId()+"\\"+photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }
}
