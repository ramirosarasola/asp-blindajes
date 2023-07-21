package com.example.aspblindajes.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkGroupProblemDTO {
    private Long id;
    private Boolean hasProblem;
    @NotEmpty
    @Length(min = 1, max = 30)
    private String problemDescription;
    @NotEmpty
    @Length(min = 1, max = 30)
    private String workGroupName;
//    @NotEmpty
//    @Length(min = 1, max = 30)
//    private Long vehicleQualityControl_id;
}
