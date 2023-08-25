package com.example.aspblindajes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkGroupProblemQueryResponse {

    private String name;
//    private double flatValue;
    private double percentageValue;

}
