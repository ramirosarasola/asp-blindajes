package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.VehicleDTO;
import com.example.aspblindajes.model.Vehicle;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VehicleToVehicleDTOConverter implements Converter<Vehicle, VehicleDTO> {
    @Override
    public VehicleDTO convert(Vehicle source) {
        return null;
    }
}
