package com.example.aspblindajes.service;
import com.example.aspblindajes.dto.BrandDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;

import java.util.List;

public interface BrandService {
    Brand saveBrand(BrandDTO brandDTO) throws ResourceAlreadyExistsException, InvalidArgumentException;
    Brand findBrandById(Long id) throws ResourceNotFoundException;
    void deleteBrandById(Long id) throws ResourceNotFoundException;
    List<Brand> findAllBrands() throws ResourceNotFoundException;
    Brand updateBrand(BrandDTO brandDTO) throws ResourceNotFoundException;
    Brand findBrandByName (String name) throws ResourceNotFoundException;
}
