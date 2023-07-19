package com.example.aspblindajes.model;

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
    @ManyToOne
    @JoinColumn(name = "workGroups_id", referencedColumnName = "id")
    private WorkGroup workGroup;
    @ManyToOne
    @JoinColumn(name = "vehicleQualityControl_id", referencedColumnName = "id")
    private VehicleQualityControl vehicleQualityControl;
}