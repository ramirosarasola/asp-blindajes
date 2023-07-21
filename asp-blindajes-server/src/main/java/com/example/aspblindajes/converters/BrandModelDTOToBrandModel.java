package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.BrandModelDTO;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BrandModelDTOToBrandModel implements Converter<BrandModelDTO, BrandModel> {

    private final BrandService brandService;
    @Override
    public BrandModel convert(BrandModelDTO source) {
        BrandModel brandModel = new BrandModel();
        try {
            brandModel.setBrand(brandService.findBrandByName(source.getBrandName()));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        brandModel.setName(source.getName());
        brandModel.setId(source.getId());
        return brandModel;
    }
}
