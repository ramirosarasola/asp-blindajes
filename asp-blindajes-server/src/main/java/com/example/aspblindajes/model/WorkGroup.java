package com.example.aspblindajes.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany (mappedBy = "workGroup" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkGroupProblem> workGroupProblemList = new ArrayList<>();
}


