package com.ismail.shop.services.impl;

import com.ismail.shop.dtos.ProductDTO;
import com.ismail.shop.dtos.ProductImagedDTO;
import com.ismail.shop.dtos.ProductPageDTO;
import com.ismail.shop.dtos.UserDTO;
import com.ismail.shop.entities.Product;
import com.ismail.shop.entities.ProductDetail;
import com.ismail.shop.entities.ProductImage;
import com.ismail.shop.exceptions.ProductNotFoundException;
import com.ismail.shop.exceptions.UserNotFoundException;
import com.ismail.shop.mappers.*;
import com.ismail.shop.repositories.ProductRepository;
import com.ismail.shop.services.ProductService;
import com.ismail.shop.utilities.Constants;
import com.ismail.shop.utilities.FileUploadUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

        if(productDTO.getDetails().size()>0){
            List<ProductDetail> productDetails = productDTO.getDetails().stream()
                    .map(productDetailDTO -> {
                        ProductDetail productDetail = this.productDetailMapper.toEntity(productDetailDTO);
                        productDetail.setProduct(product); // Set the product for the detail
                        return productDetail;
                    })
                    .collect(Collectors.toList());
            product.setDetails(productDetails);
        }

        if(productDTO.getImages().size()>0){
            List<ProductImage> productImages = productDTO.getImages().stream()
                    .map(productImagedDTO -> {
                        ProductImage productImage = this.productImageMapper.toEntity(productImagedDTO);
                        productImage.setProduct(product);
                        return productImage;
                    }).collect(Collectors.toList());
            product.setImages(productImages);
        }


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


    @Override
    public String uploadProductMainPhoto(Long id, MultipartFile file) throws IOException, ProductNotFoundException {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Could not find any product with id: " + id));
        if(!file.isEmpty()){
            String fileName= StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = Constants.PRODUCTS_IMAGES+id;

            product.setMainImage(fileName);
            this.productRepository.save(product);

            //first we clean the directory to avoid duplicate images
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir,fileName,file);
            return fileName;
        }
        return "image-not-uploaded";
    }


    @Override
    public String uploadProductExtrasPhotos(Long id, MultipartFile[] files ) throws ProductNotFoundException, IOException {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Could not find any product with id: " + id));

        if(files.length>0){
            String uploadDir = Constants.PRODUCTS_IMAGES+id+"/extras";
            for(MultipartFile multipartFile : files)
            {
                if(multipartFile.isEmpty()) continue;
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
            }
            return "uploaded";
        }
        return "not-uploaded";
    }
}
