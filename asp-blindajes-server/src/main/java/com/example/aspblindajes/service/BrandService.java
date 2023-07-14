package com.example.aspblindajes.service;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;

import java.util.List;

public interface BrandService {
    Brand saveBrand(Brand brand) throws ResourceAlreadyExistsException;
    Brand findBrandById(Long id) throws ResourceNotFoundException;
    void deleteBrandById(Long id) throws ResourceNotFoundException;
    List<Brand> findAllBrands() throws ResourceNotFoundException;
    Brand updateBrand(Brand brand) throws ResourceNotFoundException;
}
