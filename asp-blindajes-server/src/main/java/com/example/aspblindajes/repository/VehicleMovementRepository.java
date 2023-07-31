package com.example.aspblindajes.repository;

import com.example.aspblindajes.dto.VehicleMovementDTO;
import com.example.aspblindajes.model.VehicleMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleMovementRepository extends JpaRepository<VehicleMovement, Long> {
//    List<VehicleMovementDTO> findVehicleMovementsByChasis(String chasis);
}

