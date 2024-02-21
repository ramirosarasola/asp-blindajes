package com.example.aspblindajes.repository;
import com.example.aspblindajes.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle findVehicleByChasis (String chasis);
    void deleteVehicleById (String id);




    @Query(nativeQuery = true, value = "SELECT COUNT(v.id) FROM vehicle v " +
            "LEFT JOIN ( " +
            "   SELECT vqc.*, ROW_NUMBER() OVER (PARTITION BY vqc.vehicle_id ORDER BY vqc.quality_control_date DESC, vqc.id DESC) as row_num " +
            "   FROM vehicle_quality_control vqc " +
            ") AS latest_vqc ON v.id = latest_vqc.vehicle_id AND latest_vqc.row_num = 1 " +
            "WHERE v.area = 'PRODUCTION' AND (latest_vqc.vehicle_id IS NULL OR latest_vqc.can_be_checked_out = false)")
    Long countVehiclesInProductionAreaNotReadyToLeave();

    @Query(nativeQuery = true, value = "SELECT COUNT(v.id) FROM vehicle v INNER JOIN vehicle_quality_control vqc ON v.id = vqc.vehicle_id WHERE v.area = 'PRODUCTION' AND vqc.can_be_checked_out = true")
    Long countVehiclesInProductionAreaWithCanBeCheckedOutTrue();

    @Query(nativeQuery = true, value = "select COUNT(v.id) FROM vehicle v " +
            "LEFT JOIN vehicle_quality_control vqc on v.id = vqc.vehicle_id " +
            "WHERE vqc.vehicle_id is NULL AND v.area = 'LOGISTIC' ")
    Long countVehiclesInLogisticAreaWithoutQC();

    @Query(nativeQuery = true, value = "SELECT COUNT(v.id) FROM vehicle v INNER JOIN vehicle_quality_control vqc ON v.id = vqc.vehicle_id WHERE v.area = 'LOGISTIC' AND vqc.can_be_checked_out = true")
    Long countVehiclesInLogisticAreaWithCanBeCheckedOutTrue();


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


    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT v.id) FROM vehicle v " +
            "JOIN vehicle_movement vm ON vm.vehicle_id = v.id " +
            "WHERE vm.movement_type = 'PRODUCTION_CHECKOUT_TO_LOGISTIC' " +
            "AND EXTRACT(WEEK FROM vm.date_time) = :semanaParametro " +
            "AND EXTRACT(YEAR FROM vm.date_time) = :anoParametro")
    Long weeklyProductivity(@Param("anoParametro") int anoParametro,  @Param("semanaParametro") int semanaParametro);

    @Query(
            nativeQuery = true,
            value = "SELECT v.* FROM vehicle v " +
                    "JOIN client c ON v.client_id = c.id " +
                    "JOIN brand_model bm ON v.brand_model_id = bm.id " +
                    "LEFT JOIN ( " +
                    "   SELECT vqc.*, ROW_NUMBER() OVER (PARTITION BY vqc.vehicle_id ORDER BY vqc.quality_control_date DESC, vqc.id DESC) as row_num " +
                    "   FROM vehicle_quality_control vqc " +
                    ") AS latest_vqc ON v.id = latest_vqc.vehicle_id AND latest_vqc.row_num = 1 " +
                    "WHERE (:compraParametro IS NULL OR v.purchase_order = :compraParametro) " +
                    "AND (:clientName IS NULL OR c.name = :clientName) " +
                    "AND (:areaName IS NULL OR v.area = :areaName) " +
                    "AND (:modelName IS NULL OR bm.name = :modelName) " +
                    "AND (:chasis IS NULL OR v.chasis = :chasis) " +
                    "AND ((:finished IS NULL) OR " +
                    "(:finished = true AND latest_vqc.can_be_checked_out = true) OR " +
                    "(:finished = false AND (latest_vqc.can_be_checked_out = false OR latest_vqc.can_be_checked_out IS NULL)))")
    Page<Vehicle> getVehiclesByFilters(@Param("compraParametro") String compraParametro,
                                       @Param("clientName") String clientName,
                                       @Param("areaName") String areaName,
                                       @Param("modelName") String modelName,
                                       @Param("chasis") String chasis,
                                       @Param("finished") Boolean finished,
                                       Pageable pageable
    );



}
