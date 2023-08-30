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
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(source.getId());
        vehicleDTO.setClient(source.getClient().getName());
        vehicleDTO.setChasis(source.getChasis());
        vehicleDTO.setObservations(source.getObservations());
        vehicleDTO.setBrandName(source.getBrand().getName());
        vehicleDTO.setBrandModelName(source.getBrandModel().getName());
        vehicleDTO.setFordKey(source.getFordKey());
        vehicleDTO.setPurchaseOrder(source.getPurchaseOrder());
        vehicleDTO.setArea(source.getArea().toString());
        vehicleDTO.setCanBeCheckecOut(source.getQualityControlList().get(source.getQualityControlList().size() - 1).getCanBeCheckedOut());

        return vehicleDTO;
    }
}
