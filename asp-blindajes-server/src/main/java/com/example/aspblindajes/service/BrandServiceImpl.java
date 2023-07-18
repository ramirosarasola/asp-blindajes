package com.example.aspblindajes.service;

import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final static Logger LOGGER = Logger.getLogger(BrandServiceImpl.class);

    @Override
    public Brand saveBrand(Brand brand) throws ResourceAlreadyExistsException {
        if (brandRepository.findBrandByName(brand.getName()).isPresent()) {
            LOGGER.error("Failed to save brand: Brand already exists");
            throw new ResourceAlreadyExistsException("The provided brand already exists");
        }
        if (brand.getName() == null || brand.getName().isEmpty()) {
            LOGGER.error("Failed to save brand: Invalid brand name");
            throw new IllegalArgumentException("Brand name cannot be empty");
        }
        LOGGER.info("Brand saved");
        return brandRepository.save(brand);
    }

    @Override
    public Brand findBrandById(Long id) throws ResourceNotFoundException {
        return brandRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Failed to find brand: Brand not found");
                    return new ResourceNotFoundException("The brand doesn't exist.");
                });
    }

    @Override
    public void deleteBrandById(Long id) throws ResourceNotFoundException {
        if (!brandRepository.existsById(id)) {
            LOGGER.error("Failed to delete brand: Brand not found");
            throw new ResourceNotFoundException("There is no brand with the provided id");
        }
        brandRepository.deleteById(id);
    }

    @Override
    public List<Brand> findAllBrands() throws ResourceNotFoundException {
        List<Brand> brandList = brandRepository.findAll();
        if (brandList.isEmpty()) {
            LOGGER.error("Failed to retrieve brands: No brands found");
            throw new ResourceNotFoundException("There are no existing brands");
        }
        return brandList;
    }

    @Override
    public Brand updateBrand(Brand brand) throws ResourceNotFoundException {
        if (!brandRepository.existsById(brand.getId())) {
            LOGGER.error("Failed to update brand: Brand not found");
            throw new ResourceNotFoundException("The brand you are trying to update doesn't exist");
        }
        return brandRepository.save(brand);
    }
}