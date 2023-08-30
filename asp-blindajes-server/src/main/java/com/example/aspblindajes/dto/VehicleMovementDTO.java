package com.example.aspblindajes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleMovementDTO {
    private Long id;
    private String movementType;
    private String vehicleChasis;
    private String dateTime;
    private String userName;
}
