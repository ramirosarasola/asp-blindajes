package com.example.aspblindajes.service;

import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.BrandModel;

import java.util.List;

public interface BrandModelService {
    BrandModel saveBrandModel(BrandModel brandModel) throws ResourceAlreadyExistsException;
    void deleteBrandModelById(Long id) throws ResourceNotFoundException;
    BrandModel updateBrandModel(BrandModel brandModel) throws ResourceNotFoundException;
    List<BrandModel> listBrandModels() throws ResourceNotFoundException;
    BrandModel findBrandModelByName(String name) throws ResourceNotFoundException;
}
