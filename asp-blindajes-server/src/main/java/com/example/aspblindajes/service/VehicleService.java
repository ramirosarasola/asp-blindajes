package com.example.aspblindajes.service;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle saveVehicle (Vehicle vehicle);
    Vehicle findVehicleById(String id) throws ResourceNotFoundException;

    void deleteVehicleById(String id) throws ResourceNotFoundException;
    List<Vehicle> findAllVehicles() throws ResourceNotFoundException;

    Vehicle updateVehicle (Vehicle vehicle) throws ResourceNotFoundException;

}
