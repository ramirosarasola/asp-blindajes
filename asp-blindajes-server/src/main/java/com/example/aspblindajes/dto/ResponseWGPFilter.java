package com.example.aspblindajes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWGPFilter {
    private Long id;
    private String description;
    private String workGroupName;
    private String qualityControlDate;
    private String vehicleChasis;
}
