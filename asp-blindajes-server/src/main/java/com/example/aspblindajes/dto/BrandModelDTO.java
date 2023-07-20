package com.example.aspblindajes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandModelDTO {
    private Long id;
    @NotEmpty
    @Length(min = 1, max = 30)
    private String name;
    @NotEmpty
    @Length(min = 1, max = 30)
    private String brandName;
}
