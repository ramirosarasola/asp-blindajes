package com.example.aspblindajes.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class WorkGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean hasProblem;
    private String problemDescription;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "vehicleQualityControl_id", referencedColumnName = "id")
    private VehicleQualityControl vehicleQualityControl;
}







//MODIFICAR TABLAS PARA QUE CADA WORKGROUP TENGA UNA EN ESPESCIFICO Y NO UNA GENERAL PARA TODAS