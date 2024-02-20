package com.ismail.shop.services;

import com.ismail.shop.dtos.BrandDTO;
import com.ismail.shop.dtos.BrandPageDTO;
import com.ismail.shop.exceptions.BrandNotFoundException;

import java.io.IOException;
import java.util.List;

public interface BrandService {
    List<BrandDTO> getAllBrands();
    BrandDTO getOneBrandByID(Long id) throws BrandNotFoundException;
    BrandDTO createBrand(BrandDTO brandDTO);
    void deleteBrandByID(Long id) throws BrandNotFoundException;

    BrandPageDTO getPageOfBrands(int page, int size);

    byte[] getImageOfBrand(Long id) throws BrandNotFoundException, IOException;
}
