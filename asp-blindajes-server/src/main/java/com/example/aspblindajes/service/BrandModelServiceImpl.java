package com.example.aspblindajes.service;
import com.example.aspblindajes.dto.BrandModelDTO;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.repository.BrandModelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class BrandModelServiceImpl implements BrandModelService{

    private final BrandModelRepository brandModelRepository;
    private final ConversionService conversionService;

    @Override
    public BrandModel saveBrandModel(BrandModelDTO brandModelDTO) throws ResourceAlreadyExistsException {
        Optional<BrandModel> brandModelFoundByName = brandModelRepository.findBrandModelByName(brandModelDTO.getName());
        BrandModel brandModel  = conversionService.convert(brandModelDTO, BrandModel.class);
        if(brandModelFoundByName.isEmpty() && brandModel != null){
            brandModelRepository.save(brandModel);
            log.info("model saved: " + brandModel);
            return brandModel;
        }
        throw new ResourceAlreadyExistsException("The provided model already exists or might be null");
    }
    @Override
    public void deleteBrandModelById(Long id) throws ResourceNotFoundException{
        boolean brandModelFound = brandModelRepository.findById(id).isPresent();
        if(brandModelFound){
            brandModelRepository.deleteById(id);
        }
        throw new ResourceNotFoundException("Not Found");
    }
    @Override
    public BrandModel updateBrandModel(BrandModelDTO brandModelDTO) throws ResourceNotFoundException {
        Optional<BrandModel> brandModelFound = brandModelRepository.findById(brandModelDTO.getId());
        BrandModel brandModel = conversionService.convert(brandModelDTO, BrandModel.class);
        if (brandModelFound.isPresent() && brandModel != null) {
            return brandModelRepository.save(brandModel);
        }
        throw new ResourceNotFoundException("Not Found");
    }
    @Override
    public List<BrandModel> listBrandModels() throws ResourceNotFoundException{
        List<BrandModel> allBrandModels = new ArrayList<>();
        allBrandModels = brandModelRepository.findAll();
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
