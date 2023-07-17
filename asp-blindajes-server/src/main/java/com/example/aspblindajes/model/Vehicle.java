package com.example.aspblindajes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    private String chasis;
    private String observations;
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "brandModel_id", referencedColumnName = "id")
    private BrandModel brandModel;

//    chequear con respecto al mapeo anotaciones com fetch type, cascade types, etc.

    @OneToMany(mappedBy = "vehicle")
    private List<VehicleQualityControl> qualityControlList;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;


//    private Cliente destino;
//    cliente a quien va destinado el vehiculo
//    carga de admin

}
