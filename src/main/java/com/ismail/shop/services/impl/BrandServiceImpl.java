package com.ismail.shop.services.impl;

import com.ismail.shop.dtos.BrandDTO;
import com.ismail.shop.dtos.BrandPageDTO;
import com.ismail.shop.dtos.CategoryDTO;
import com.ismail.shop.dtos.CategoryPageDTO;
import com.ismail.shop.entities.Brand;
import com.ismail.shop.entities.Category;
import com.ismail.shop.exceptions.BrandNotFoundException;
import com.ismail.shop.mappers.BrandMapper;
import com.ismail.shop.repositories.BrandRepository;
import com.ismail.shop.services.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private BrandRepository brandRepository;
    private BrandMapper mapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper mapper) {
        this.brandRepository = brandRepository;
        this.mapper = mapper;
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        List<BrandDTO> brandDTOS = brandRepository.findAll().stream().map(brand -> {
            return this.mapper.fromEntity(brand);
        }).collect(Collectors.toList());
        return brandDTOS;
    }

    @Override
    public BrandDTO getOneBrandByID(Long id) throws BrandNotFoundException {
        Brand brand = this.brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException("Could not find any brand with id " + id));
        BrandDTO brandDTO = this.mapper.fromEntity(brand);
        return brandDTO;
    }

    @Override
    public BrandDTO createBrand(BrandDTO brandDTO) {
        Brand brand = this.mapper.fromDTO(brandDTO);
        Brand savedBrand = this.brandRepository.save(brand);
        return this.mapper.fromEntity(savedBrand);
    }

    @Override
    public void deleteBrandByID(Long id) throws BrandNotFoundException {
        Brand brand = this.brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException("Could not find any brand with id " + id));
        this.brandRepository.deleteById(brand.getId());
    }

    @Override
    public BrandPageDTO getPageOfBrands(int page, int size)
    {
        if(page< 0) page = 0;
        if(size<0) size = 10;
        Page<Brand> brandPage = this.brandRepository.findAll(PageRequest.of(page,size));

        List<BrandDTO> brandDTOS = brandPage.getContent()
                .stream().map(category -> this.mapper.fromEntity(category)).collect(Collectors.toList());

        BrandPageDTO brandPageDTO = new BrandPageDTO();
        brandPageDTO.setCurrentPage(page);
        brandPageDTO.setPageSize(size);
        brandPageDTO.setTotalPages(brandPage.getTotalPages());
        brandPageDTO.setBrandDTOS(brandDTOS);
        return brandPageDTO;
    }
}
