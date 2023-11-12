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
        if(brandModelFoundByName.isEmpty() && brandModel != null && brandOptional.isPresent()){
            brandModelRepository.save(brandModel);
            log.info("model saved: " + brandModel);
            return brandModel;
        }
        log.error("The model trying to save already exists or might be null");
        throw new ResourceAlreadyExistsException("The provided model already exists or might be null");
    }
    @Override
    public void deleteBrandModelById(Long id) throws ResourceNotFoundException{
        boolean brandModelFound = brandModelRepository.findById(id).isEmpty();
        if(brandModelFound){
            log.error("The model you are trying to delete doesn't exits.");
            throw new ResourceNotFoundException("The model doesn't exits.");
        }
        brandModelRepository.deleteById(id);
        log.info("Model deleted successfully");
    }
    @Override
    public BrandModel updateBrandModel(BrandModelDTO brandModelDTO) throws ResourceNotFoundException {
        Optional<BrandModel> brandModelFound = brandModelRepository.findById(brandModelDTO.getId());
        BrandModel brandModel = brandModelDTOToBrandModel.convert(brandModelDTO);
        Optional<Brand> brandOptional = brandRepository.findBrandByName(brandModelDTO.getBrandName());
        if (brandModelFound.isPresent() && brandModel != null && brandOptional.isPresent()) {
            log.info("Model updated successfully");
            return brandModelRepository.save(brandModel);
        }
        log.error("Model Not Found");
        throw new ResourceNotFoundException("Brand Model Not Found");
    }
    @Override
    public List<BrandModel> listBrandModels() throws ResourceNotFoundException{
        List<BrandModel> allBrandModels = brandModelRepository.findAll();
        if(allBrandModels.isEmpty()){
            log.error("There are no models created");
            throw new ResourceNotFoundException("There are no models created");
        }
        log.info("All models found successfully");
        return allBrandModels;
    }
    @Override
    public BrandModel findBrandModelByName(String name) throws ResourceNotFoundException{
        Optional<BrandModel> brandModel = brandModelRepository.findBrandModelByName(name);
        if(brandModel.isPresent()){
            log.info("Model found by name");
            return brandModel.get();
        }
        log.error("Model could not be found by name");
        throw new ResourceNotFoundException("Model not found");
    }

    public BrandModel modelSetHidden(Long id) throws ResourceNotFoundException{
        Optional<BrandModel> brandModelO = brandModelRepository.findById(id);
        if(brandModelO.isPresent()){
            BrandModel brandModel = brandModelO.get();
            brandModel.setHidden(!brandModel.getHidden());
            brandModelRepository.save(brandModel);
            log.info("Hidden field changed");
            return brandModel;
        }
        log.error("Model could not be found by name");
        throw new ResourceNotFoundException("Model not found");
    }

    @Override
    public List<BrandModel> listBrandModelsHiddenFalse() throws ResourceNotFoundException {
        List<BrandModel> brandModelList = brandModelRepository.findAllBrandModelHiddenFalse();
        if(brandModelList.isEmpty()){
            log.error("There are no unhidden models");
            throw new ResourceNotFoundException("There are no unhidden models");
        }
        log.info("All unhidden models found successfully");
        return brandModelList;
    }

}
