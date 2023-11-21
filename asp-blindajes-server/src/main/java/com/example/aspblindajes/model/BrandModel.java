package com.example.aspblindajes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BrandModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean hidden = false;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @JsonIgnore
    @OneToMany(mappedBy = "brandModel", cascade = CascadeType.ALL)
    private List<Vehicle> vehicleList = new ArrayList<>();
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "model_workgroup",
            joinColumns = @JoinColumn(name = "brandModel_id"),
            inverseJoinColumns = @JoinColumn(name = "workGroup_id"))
    private List<WorkGroup> workGroupsList = new ArrayList<>();


}
