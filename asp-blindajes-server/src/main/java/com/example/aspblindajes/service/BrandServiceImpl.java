package com.example.aspblindajes.service;
import com.example.aspblindajes.converters.BrandDTOToBrand;
import com.example.aspblindajes.converters.BrandModelDTOToBrandModel;
import com.example.aspblindajes.dto.BrandDTO;
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
    private final BrandDTOToBrand brandDTOToBrand;

    @Override
    public Brand saveBrand(BrandDTO brandDTO) throws ResourceAlreadyExistsException, InvalidArgumentException {
        Brand brand = brandDTOToBrand.convert(brandDTO);
        if (brandRepository.findBrandByName(brandDTO.getName()).isPresent()) {
            log.error("Failed to save brand: Brand already exists");
            throw new ResourceAlreadyExistsException("The provided brand already exists");
        }
        if (brand != null){
            log.info("Brand saved");
            return brandRepository.save(brand);
        }
       throw new InvalidArgumentException("Invalid information provided");
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
        if (!brandRepository.existsById(id)) {
            log.error("Failed to delete brand: Brand not found");
            throw new ResourceNotFoundException("There is no brand with the provided id");
        }
        log.info("Brand deleted successfully");
        brandRepository.deleteById(id);
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
    public Brand updateBrand(BrandDTO brandDTO) throws ResourceNotFoundException {
        if (!brandRepository.existsById(brandDTO.getId())) {
            log.error("Failed to update brand: Brand not found");
            throw new ResourceNotFoundException("The brand you are trying to update doesn't exist");
        }
        Brand savedBrand = brandRepository.save(brandDTOToBrand.convert(brandDTO));
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