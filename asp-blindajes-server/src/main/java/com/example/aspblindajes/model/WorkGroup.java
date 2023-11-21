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
    private Boolean hidden = false;
    private String examples;
    @JsonIgnore
    @OneToMany (mappedBy = "workGroup" , cascade = CascadeType.ALL)
    private List<WorkGroupProblem> workGroupProblemList = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "workGroupsList")
    private List<BrandModel> brandModelList = new ArrayList<>();
}


