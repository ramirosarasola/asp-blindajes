package com.example.aspblindajes.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class WorkGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean hasProblem;
    private String problemDescription;
    @ManyToOne
    @JoinColumn(name = "vehicleQualityControl_id", referencedColumnName = "id")
    private VehicleQualityControl vehicleQualityControl;
}
