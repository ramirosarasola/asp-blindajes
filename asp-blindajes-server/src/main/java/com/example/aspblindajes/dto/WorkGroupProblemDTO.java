package com.example.aspblindajes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkGroupProblemDTO {
    private Boolean hasProblem;
    private String problemDescription;
    private String workGroupName;
    private Long vehicleQualityControl_id;
}
