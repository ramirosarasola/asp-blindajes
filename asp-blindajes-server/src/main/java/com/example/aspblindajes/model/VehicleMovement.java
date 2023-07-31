package com.example.aspblindajes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated (EnumType.STRING)
    private MovementType movementType;
    @ManyToOne
    @JoinColumn(name = "vehicle_chasis", referencedColumnName = "chasis")
    @JsonIgnore
    private Vehicle vehicle;
    private LocalDate date = LocalDate.now();

}
