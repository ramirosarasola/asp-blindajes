package com.example.aspblindajes.service;

import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.repository.BrandModelRepository;
import org.springframework.stereotype.Service;

@Service
public class BrandModelService {
    private BrandModelRepository brandModelRepository;

    public BrandModel saveBrandModel(BrandModel brandModel) throws Exception{
        boolean brandModelFound = brandModelRepository.findById(brandModel.getId()).isPresent();
        if(brandModelFound){
            return brandModelRepository.save(brandModel);
        }
        throw new Exception("Not found");

    }
}
