package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.VehicleMovementDTO;
import com.example.aspblindajes.model.VehicleMovement;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VehicleMovementeToVehicleMovementDTO implements Converter<VehicleMovement, VehicleMovementDTO> {
    @Override
    public VehicleMovementDTO convert(VehicleMovement source) {
        VehicleMovementDTO vehicleMovementDTO = new VehicleMovementDTO();
        vehicleMovementDTO.setMovementType(source.getMovementType().name());
        vehicleMovementDTO.setVehicleChasis(source.getVehicle().getChasis());
        vehicleMovementDTO.setId(source.getId());
        vehicleMovementDTO.setDate(source.getDate());

        return vehicleMovementDTO;
    }
}
