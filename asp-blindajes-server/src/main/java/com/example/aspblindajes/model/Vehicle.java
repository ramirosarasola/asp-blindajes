package com.example.aspblindajes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    private String chasis;
    private String observations;
    private Brand brand;
    private BrandModel brandModel;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private VehicleQualityControl qualityControl;

}
