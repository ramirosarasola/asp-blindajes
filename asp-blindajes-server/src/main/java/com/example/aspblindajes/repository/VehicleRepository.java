package com.example.aspblindajes.repository;
import com.example.aspblindajes.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle findVehicleByChasis (String chasis);
    Void deleteVehicleByChasis (String chasis);


    @Query(nativeQuery = true,  value = "SELECT COUNT(v.id) FROM vehicle v INNER JOIN vehicle_quality_control vqc ON v.id = vqc.vehicle_id WHERE v.area = 'LOGISTIC' AND vqc.can_be_checked_out = false")
    Long countVehiclesInLogisticAreaWithCanBeCheckedOutFalse();


    @Query(nativeQuery = true, value = "SELECT COUNT(v.id) FROM vehicle v INNER JOIN vehicle_quality_control vqc ON v.id = vqc.vehicle_id WHERE v.area = 'LOGISTIC' AND vqc.can_be_checked_out = true")
    Long countVehiclesInLogisticAreaWithCanBeCheckedOutTrue();

    @Query(nativeQuery = true, value = "SELECT COUNT(v.id) FROM vehicle v WHERE v.area = 'PRODUCTION'")
    Long countVehiclesInProductionArea();



}
