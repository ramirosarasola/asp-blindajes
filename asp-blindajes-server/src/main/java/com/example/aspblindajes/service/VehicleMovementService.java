package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.VehicleMovementDTO;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.VehicleMovement;
import java.util.List;

public interface VehicleMovementService {
    VehicleMovement saveVehicleMovement (String chasis, String userName) throws Exception;

    VehicleMovementDTO findVehicleMovementById (Long id) throws ResourceNotFoundException;

    List<VehicleMovementDTO> findAllVehicleMovements () throws ResourceNotFoundException;

    void deleteVehicleMovementById (Long id) throws ResourceNotFoundException;

    VehicleMovement updateVehicleMovement (VehicleMovement vehicleMovement) throws ResourceNotFoundException;

    List<VehicleMovementDTO> findVehicleMovementsByChasis (String chasis) throws ResourceNotFoundException;





}
