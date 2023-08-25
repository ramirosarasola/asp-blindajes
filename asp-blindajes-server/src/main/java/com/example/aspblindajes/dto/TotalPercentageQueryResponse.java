package com.example.aspblindajes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalPercentageQueryResponse {
    private Double porcentajeTotalDeProblemasDeGruposDeTrabajoControlados;
    private Long cantidadTotalDeGruposDeTrabajoControlados;
}
