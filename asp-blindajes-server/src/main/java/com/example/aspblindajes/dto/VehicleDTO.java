package com.example.aspblindajes.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    @NotEmpty
    @Length(min = 1, max = 20)
    private String chasis;
    @NotEmpty
    @Length(min = 1, max = 30)
    private String brandName;
    @NotEmpty
    @Length(min = 1, max = 30)
    private String brandModelName;
    @NotEmpty
    @Length(min = 1, max = 50)
    private String observations;
    @NotEmpty
    @Length(min = 1, max = 30)
    private String purchaseOrder;
    @NotEmpty
    @Length(min = 1, max = 40)
    private String client;
}