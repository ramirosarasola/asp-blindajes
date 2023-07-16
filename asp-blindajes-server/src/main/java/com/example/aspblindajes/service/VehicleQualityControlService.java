package com.example.aspblindajes.service;

import com.example.aspblindajes.model.VehicleQualityControl;

import java.util.List;

public interface VehicleQualityControlService {

    VehicleQualityControl saveVehicleQualityControl (VehicleQualityControl vehicleQualityControl);

    void deleteVehicleQualityControlById (Long id);
    VehicleQualityControl findVehicleQualityControlById (Long id);
    List<VehicleQualityControl> findAllVehicleQualiControl ();

    VehicleQualityControl updateVehicleQualityControl (VehicleQualityControl vehicleQualityControl);
}
