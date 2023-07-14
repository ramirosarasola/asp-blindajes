package com.example.aspblindajes.service;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.repository.BrandModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BrandModelServiceImpl implements BrandModelService{

    private final BrandModelRepository brandModelRepository;
    @Override
    public BrandModel saveBrandModel(BrandModel brandModel) throws ResourceAlreadyExistsException {
        Optional<BrandModel> brandModelFoundByName = brandModelRepository.findBrandModelByName(brandModel.getName());
        if(brandModelFoundByName.isEmpty()){
            return brandModelRepository.save(brandModel);
        }
        throw new ResourceAlreadyExistsException("The provided model already exists");
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
    public BrandModel updateBrandModel(BrandModel brandModel) throws ResourceNotFoundException {
        Optional<BrandModel> brandModelFound = brandModelRepository.findById(brandModel.getId());
        if (brandModelFound.isPresent()) {
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
