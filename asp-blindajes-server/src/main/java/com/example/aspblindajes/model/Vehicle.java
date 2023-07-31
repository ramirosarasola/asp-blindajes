package com.example.aspblindajes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    private String chasis;
    private String observations;
    private String purchaseOrder;
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "brandModel_id", referencedColumnName = "id")
    private BrandModel brandModel;
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<VehicleQualityControl> qualityControlList;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    private String fordKey;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleMovement> vehicleMovementList;
    @Enumerated (EnumType.STRING)
    private Area area = Area.LOGISTIC;


    //    private LocalDate checkInDate = LocalDate.now(); -> todo quit checkInDates from vehicles
//    private LocalDate checkOutDate;

}
