package com.example.aspblindajes.dto;

import com.example.aspblindajes.model.WorkGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleQualityControlDTO {
    private String chasis;
    private List<WorkGroups> workGroupsList;
}
