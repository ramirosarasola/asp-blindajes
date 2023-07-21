package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.VehicleDTO;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.model.Vehicle;
import com.example.aspblindajes.repository.BrandModelRepository;
import com.example.aspblindajes.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class VehicleDTOToVehicleConverter implements Converter<VehicleDTO, Vehicle> {
    private final BrandRepository brandRepository;
    private final BrandModelRepository brandModelRepository;

    @Override
    public Vehicle convert(VehicleDTO source) {
        Optional<Brand> brand = brandRepository.findBrandByName(source.getBrandName());
        Optional<BrandModel> brandModel = brandModelRepository.findBrandModelByName(source.getBrandModelName());
        Vehicle vehicle = new Vehicle();
        if (brand.isPresent() && brandModel.isPresent()) {
            vehicle.setChasis(source.getChasis());
            vehicle.setBrand(brand.get());
            vehicle.setBrandModel(brandModel.get());
            vehicle.setObservations(source.getObservations());
        }
        return vehicle;
    }
}
