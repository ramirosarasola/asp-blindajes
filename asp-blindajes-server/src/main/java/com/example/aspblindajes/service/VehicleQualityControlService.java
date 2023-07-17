package com.example.aspblindajes.service;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.VehicleQualityControl;

import java.util.List;

public interface VehicleQualityControlService {

    VehicleQualityControl saveVehicleQualityControl (VehicleQualityControl vehicleQualityControl);

    void deleteVehicleQualityControlById (Long id) throws ResourceNotFoundException;
    VehicleQualityControl findVehicleQualityControlById (Long id) throws ResourceNotFoundException;
    List<VehicleQualityControl> findAllVehicleQualiControl () throws ResourceNotFoundException;

    VehicleQualityControl updateVehicleQualityControl (VehicleQualityControl vehicleQualityControl) throws ResourceNotFoundException;
}
