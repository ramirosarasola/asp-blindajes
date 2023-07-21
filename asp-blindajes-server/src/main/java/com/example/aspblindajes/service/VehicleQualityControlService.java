package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.VehicleQualityControlDTO;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.VehicleQualityControl;

import java.util.List;

public interface VehicleQualityControlService {

    VehicleQualityControl saveVehicleQualityControl (VehicleQualityControlDTO vehicleQualityControlDTO);

    void deleteVehicleQualityControlById (Long id) throws ResourceNotFoundException;
    VehicleQualityControl findVehicleQualityControlById (Long id) throws ResourceNotFoundException;
    List<VehicleQualityControl> findAllVehicleQualityControl() throws ResourceNotFoundException;

    VehicleQualityControl updateVehicleQualityControl (VehicleQualityControlDTO vehicleQualityControlDTO) throws ResourceNotFoundException;
}
