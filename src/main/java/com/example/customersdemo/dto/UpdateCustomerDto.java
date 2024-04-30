package com.example.customersdemo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCustomerDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String fullName;

    @Size(min = 6, max = 14)
    @Pattern(regexp = "\\+[0-9]+")
    private String phone;
}
