package com.example.aspblindajes.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;
    private LocalDate qualityControlDate = LocalDate.now();
    private Boolean canBeCheckedOut = false;

    @OneToMany (mappedBy = "vehicleQualityControl", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkGroupProblem> workGroupProblemList;

}
