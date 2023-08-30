package com.example.aspblindajes.repository;

import com.example.aspblindajes.dto.VehicleMovementDTO;
import com.example.aspblindajes.model.VehicleMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleMovementRepository extends JpaRepository<VehicleMovement, Long> {
//    List<VehicleMovementDTO> findVehicleMovementsByChasis(String chasis);

    @Query(nativeQuery = true, value = "SELECT vm.* FROM vehicle_movement vm "+
            "WHERE (:mtName IS NULL OR vm.movement_type = :mtName) " +
            "AND (:vehicleId IS NULL OR  vm.vehicle_id = :vehicleId) " +
            "AND (:startDate IS NULL OR  vm.date_time >= :startDate) " +
            "AND (:endDate IS NULL OR  vm.date_time <= :endDate)")
    List<VehicleMovement> getMovementsByFilter (@Param(value = "mtName")String mtName, @Param(value = "vehicleId") String vehicleId, @Param(value = "startDate") LocalDateTime startDate, @Param(value = "endDate") LocalDateTime endDate);
}

