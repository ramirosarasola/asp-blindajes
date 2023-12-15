package com.example.aspblindajes.repository;

import com.example.aspblindajes.dto.VehicleMovementDTO;
import com.example.aspblindajes.model.VehicleMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleMovementRepository extends JpaRepository<VehicleMovement, Long> {
//    List<VehicleMovementDTO> findVehicleMovementsByChasis(String chasis);

    @Query(nativeQuery = true, value = "SELECT vm.* FROM vehicle_movement vm "+
            "INNER JOIN vehicle v ON vm.vehicle_id = v.vehicle_id " +
            "WHERE (:mtName IS NULL OR vm.movement_type = :mtName) " +
            "AND (:vehicleChasis IS NULL OR  v.chasis LIKE CONCAT('%', :vehicleId, '%'))  " +
            "AND (:startDate IS NULL OR  vm.date_time >= :startDate) " +
            "AND (:userId IS NULL OR vm.user_id = :userId) " +
            "AND (:endDate IS NULL OR  vm.date_time <= :endDate)")
    List<VehicleMovement> getMovementsByFilter (@Param(value = "mtName")String mtName,
                                                @Param(value = "vehicleChasis") String vehicleChasis,
                                                @Param(value = "startDate") LocalDateTime startDate,
                                                @Param(value = "userId") Long userId,
                                                @Param(value = "endDate") LocalDateTime endDate);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM vehicle_movement "+
            "WHERE vehicle_id = :vehicleId")
    void deleteMovementByVehicleId (@Param(value = "vehicleId") String vehicleId);
}

