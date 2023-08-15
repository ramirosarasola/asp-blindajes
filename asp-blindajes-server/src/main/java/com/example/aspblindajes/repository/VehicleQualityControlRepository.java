package com.example.aspblindajes.repository;
import com.example.aspblindajes.model.VehicleQualityControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VehicleQualityControlRepository extends JpaRepository<VehicleQualityControl, Long> {
    List<VehicleQualityControl> findVehicleQualityControlByVehicleChasis (String chasis);
}
