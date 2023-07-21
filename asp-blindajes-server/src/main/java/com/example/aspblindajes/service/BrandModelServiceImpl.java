package com.example.aspblindajes.service;
import com.example.aspblindajes.converters.BrandModelDTOToBrandModel;
import com.example.aspblindajes.dto.BrandModelDTO;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.repository.BrandModelRepository;
import com.example.aspblindajes.repository.BrandRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class BrandModelServiceImpl implements BrandModelService{

    private final BrandModelRepository brandModelRepository;
    private final BrandModelDTOToBrandModel brandModelDTOToBrandModel;
    private final BrandRepository brandRepository;


    @Override
    public BrandModel saveBrandModel(BrandModelDTO brandModelDTO) throws ResourceAlreadyExistsException {
        Optional<BrandModel> brandModelFoundByName = brandModelRepository.findBrandModelByName(brandModelDTO.getName());
        BrandModel brandModel  = brandModelDTOToBrandModel.convert(brandModelDTO);
        Optional<Brand> brandOptional = brandRepository.findBrandByName(brandModelDTO.getBrandName());
        log.info(brandModelDTO.getName());
        if(brandModelFoundByName.isEmpty() && brandModel != null && brandOptional.isPresent()){
            brandModelRepository.save(brandModel);
            log.info("model saved: " + brandModel);
            return brandModel;
        }
        throw new ResourceAlreadyExistsException("The provided model already exists or might be null");
    }
    @Override
    public void deleteBrandModelById(Long id) throws ResourceNotFoundException{
        boolean brandModelFound = brandModelRepository.findById(id).isEmpty();
        if(brandModelFound){
            throw new ResourceNotFoundException("The model doesn't exits.");
        }
        brandModelRepository.deleteById(id);
    }
    @Override
    public BrandModel updateBrandModel(BrandModelDTO brandModelDTO) throws ResourceNotFoundException {
        Optional<BrandModel> brandModelFound = brandModelRepository.findById(brandModelDTO.getId());
        BrandModel brandModel = brandModelDTOToBrandModel.convert(brandModelDTO);
        Optional<Brand> brandOptional = brandRepository.findBrandByName(brandModelDTO.getBrandName());
        if (brandModelFound.isPresent() && brandModel != null && brandOptional.isPresent()) {
            return brandModelRepository.save(brandModel);
        }
        throw new ResourceNotFoundException("Not Found");
    }
    @Override
    public List<BrandModel> listBrandModels() throws ResourceNotFoundException{
        List<BrandModel> allBrandModels = brandModelRepository.findAll();
        if(allBrandModels.isEmpty()){
            throw new ResourceNotFoundException("Not Found");
        }
        return allBrandModels;
    }
    @Override
    public BrandModel findBrandModelByName(String name) throws ResourceNotFoundException{
        Optional<BrandModel> brandModel = brandModelRepository.findBrandModelByName(name);
        if(brandModel.isPresent()){
            return brandModel.get();
        }
        throw new ResourceNotFoundException("Not found");

    }
}
