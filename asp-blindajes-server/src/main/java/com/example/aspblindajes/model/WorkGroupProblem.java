package com.example.aspblindajes.model;

import com.example.aspblindajes.dto.VehicleQualityControlDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkGroupProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean hasProblem;
    private String problemDescription;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "work_groups_id", referencedColumnName = "id")
    private WorkGroup workGroup;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vehicle_quality_control_id", referencedColumnName = "id")
    private VehicleQualityControl vehicleQualityControl;
}