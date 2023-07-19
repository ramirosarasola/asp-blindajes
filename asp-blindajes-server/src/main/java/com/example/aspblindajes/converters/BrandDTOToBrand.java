package com.example.aspblindajes.converters;


import com.example.aspblindajes.dto.BrandDTO;
import com.example.aspblindajes.model.Brand;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BrandDTOToBrand implements Converter<BrandDTO, Brand> {
    @Override
    public Brand convert(BrandDTO source) {
        Brand brand = new Brand();
        brand.setName(source.getName());
        return brand;
    }
}
