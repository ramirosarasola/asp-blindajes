package com.example.aspblindajes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleMovementDTO {
    private Long id;
    private String movementType;
    private String vehicleChasis;
    private LocalDate date;
}
