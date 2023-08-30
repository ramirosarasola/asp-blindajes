package com.example.aspblindajes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @JsonIgnore
    private Vehicle vehicle;
    private LocalDateTime dateTime = LocalDateTime.now();
    @ManyToOne
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private User user;


    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }

}
