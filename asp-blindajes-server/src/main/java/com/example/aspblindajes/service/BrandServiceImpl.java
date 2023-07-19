package com.example.aspblindajes.service;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.repository.BrandRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Brand saveBrand(Brand brand) throws ResourceAlreadyExistsException, InvalidArgumentException {
        if (brandRepository.findBrandByName(brand.getName()).isPresent()) {
            log.error("Failed to save brand: Brand already exists");
            throw new ResourceAlreadyExistsException("The provided brand already exists");
        }
        if (brand.getName() == null || brand.getName().isEmpty()) {
            log.error("Failed to save brand: Invalid brand name");
            throw new InvalidArgumentException("Brand name cannot be empty");
        }
        log.info("Brand saved");
        return brandRepository.save(brand);
    }

    @Override
    public Brand findBrandById(Long id) throws ResourceNotFoundException {
        return brandRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Failed to find brand: Brand not found");
                    return new ResourceNotFoundException("The brand doesn't exist.");
                });
    }

    @Override
    public void deleteBrandById(Long id) throws ResourceNotFoundException {
        if (brandRepository.existsById(id)) {
            log.info("Brand deleted successfully");
            brandRepository.deleteById(id);
        }
        log.error("Failed to delete brand: Brand not found");
        throw new ResourceNotFoundException("There is no brand with the provided id");
    }

    @Override
    public List<Brand> findAllBrands() throws ResourceNotFoundException {
        List<Brand> brandList = brandRepository.findAll();
        if (brandList.isEmpty()) {
            log.error("Failed to retrieve brands: No brands found");
            throw new ResourceNotFoundException("There are no existing brands");
        }
        return brandList;
    }

    @Override
    public Brand updateBrand(Brand brand) throws ResourceNotFoundException {
        if (!brandRepository.existsById(brand.getId())) {
            log.error("Failed to update brand: Brand not found");
            throw new ResourceNotFoundException("The brand you are trying to update doesn't exist");
        }
        Brand savedBrand = brandRepository.save(brand);
        log.info("Brand updated successfully");
        return savedBrand;
    }
    @Override
    public Brand findBrandByName(String name) throws ResourceNotFoundException {
        return brandRepository.findBrandByName(name)
                .orElseThrow(() -> {
                    log.error("Failed to find brand: Brand not found");
                    return new ResourceNotFoundException("The brand doesn't exist.");
                });
    }
}