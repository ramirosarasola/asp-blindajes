package com.example.aspblindajes.repository;
import com.example.aspblindajes.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
