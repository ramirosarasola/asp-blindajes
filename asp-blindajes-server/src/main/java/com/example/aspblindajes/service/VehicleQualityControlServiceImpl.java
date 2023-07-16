package com.example.aspblindajes.service;
import com.example.aspblindajes.model.VehicleQualityControl;
import com.example.aspblindajes.repository.VehicleQualityControlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleQualityControlServiceImpl implements VehicleQualityControlService{

    private final VehicleQualityControlRepository vehicleQualityControlRepository;
    private final WorkGroupsService workGroupsService;

    @Override
    public VehicleQualityControl saveVehicleQualityControl(VehicleQualityControl vehicleQualityControl) {
        return null;
    }

    @Override
    public void deleteVehicleQualityControlById(Long id) {

    }

    @Override
    public VehicleQualityControl findVehicleQualityControlById(Long id) {
        return null;
    }

    @Override
    public List<VehicleQualityControl> findAllVehicleQualiControl() {
        return null;
    }

    @Override
    public VehicleQualityControl updateVehicleQualityControl(VehicleQualityControl vehicleQualityControl) {
        return null;
    }
}
