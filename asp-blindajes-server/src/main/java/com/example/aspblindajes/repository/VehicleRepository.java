package com.example.aspblindajes.repository;
import com.example.aspblindajes.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle findVehicleByChasis (String chasis);
    Void deleteVehicleByChasis (String chasis);


    @Query(nativeQuery = true,  value = "SELECT COUNT(v.id) FROM vehicle v INNER JOIN vehicle_quality_control vqc ON v.id = vqc.vehicle_id WHERE v.area = 'LOGISTIC' AND vqc.can_be_checked_out = false")
    Long countVehiclesInLogisticAreaWithCanBeCheckedOutFalse();


    @Query(nativeQuery = true, value = "SELECT COUNT(v.id) FROM vehicle v INNER JOIN vehicle_quality_control vqc ON v.id = vqc.vehicle_id WHERE v.area = 'LOGISTIC' AND vqc.can_be_checked_out = true")
    Long countVehiclesInLogisticAreaWithCanBeCheckedOutTrue();

    @Query(nativeQuery = true, value = "SELECT COUNT(v.id) FROM vehicle v WHERE v.area = 'PRODUCTION'")
    Long countVehiclesInProductionArea();


    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT v.id) FROM vehicle v " +
            "JOIN vehicle_movement vm ON v.id = vm.vehicle_id " +
            "WHERE vm.movement_type = 'PRODUCTION_CHECKOUT_TO_LOGISTIC' " +
            "AND EXTRACT(MONTH FROM vm.date_time) = :mesParametro " +
            "AND EXTRACT(YEAR FROM vm.date_time) = :anoParametro")
    Long monthlyProductivity(@Param("mesParametro") int mesParametro,  @Param("anoParametro") int anoParametro);


    @Query(nativeQuery = true, value = "SELECT EXTRACT(MONTH FROM vm.date_time) AS mes, COUNT(DISTINCT v.id) AS productividad " +
            "FROM vehicle v " +
            "JOIN vehicle_movement vm ON vm.vehicle_id = v.id " +
            "WHERE vm.movement_type = 'PRODUCTION_CHECKOUT_TO_LOGISTIC' " +
            "AND EXTRACT(YEAR FROM vm.date_time) = :anoParametro " +
            "GROUP BY mes")
    List<Object[]> allMonthlyProductivity(@Param("anoParametro") int anoParametro);

//
//
//    @Query("SELECT COUNT(DISTINCT v.id) FROM Vehicle v " +
//            "JOIN v.vehicleMovementList vm " +
//            "WHERE vm.movementType = 'PRODUCTION_CHECKOUT_TO_LOGISTIC' " +
//            "AND EXTRACT(YEAR FROM vm.dateTime) = :anoParametro " +
//            "AND EXTRACT(WEEK FROM vm.dateTime) = :semanaParametro")
//    Long weeklyProductivity(@Param("anoParametro") int anoParametro,  @Param("semanaParametro") int semanaParametro);


}
