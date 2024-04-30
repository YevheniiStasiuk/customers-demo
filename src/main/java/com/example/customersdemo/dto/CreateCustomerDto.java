package com.example.customersdemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateCustomerDto extends UpdateCustomerDto {
    @NotNull
    @Email
    @Size(min = 2, max = 100)
    private String email;
}
