package com.example.aspblindajes.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BrandDTO {
    private Long id;
    @NotEmpty
    @Length(min = 1, max = 30)
    private String name;
}
