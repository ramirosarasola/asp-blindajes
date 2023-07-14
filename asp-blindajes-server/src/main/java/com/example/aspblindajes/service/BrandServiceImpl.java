package com.example.aspblindajes.service;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService{
    private final BrandRepository brandRepository;

    @Override
    public Brand saveBrand(Brand brand) throws ResourceAlreadyExistsException {
        Optional<Brand> brandFound = brandRepository.findBrandByName(brand.getName());
        if(brandFound.isEmpty()){
            return brandRepository.save(brand);
        }
        throw new ResourceAlreadyExistsException("The provided brand already exists");
    }

    @Override
    public Brand findBrandById(Long id) throws ResourceNotFoundException {
        Optional<Brand> brandFound = brandRepository.findById(id);
        if(brandFound.isEmpty()){
            throw new ResourceNotFoundException("The brand doesn't exist.");
        }
        return brandFound.get();
    }

    @Override
    public void deleteBrandById(Long id) throws ResourceNotFoundException {
        Optional<Brand> brandFound = brandRepository.findById(id);
        if (brandFound.isPresent()){
            brandRepository.deleteById(id);
        }
        throw new ResourceNotFoundException("There is no brand with the provided id");
    }

    @Override
    public List<Brand> findAllBrands() throws ResourceNotFoundException {
        List<Brand> brandList = brandRepository.findAll();
        if (brandList.size()>0){
            return brandList;
        }
        throw new ResourceNotFoundException("There are no existing brands");
    }

    @Override
    public Brand updateBrand(Brand brand) throws ResourceNotFoundException {
        Optional<Brand> optionalBrand = brandRepository.findById(brand.getId());
        if (optionalBrand.isPresent()){
            return brandRepository.save(brand);
        }
        throw new ResourceNotFoundException("The brand you are trying to update doesn't exist");
    }
}
