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
public class VehicleQualityControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "vehicle_chasis", referencedColumnName = "chasis")
    private Vehicle vehicle;
    private LocalDate qualityControlDate;
    private Boolean isValid = false;
    @OneToMany (mappedBy = "vehicleQualityControl")
    private List<WorkGroups> workGroupsList;

}
