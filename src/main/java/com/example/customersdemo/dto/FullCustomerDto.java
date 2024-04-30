package com.example.customersdemo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FullCustomerDto extends CreateCustomerDto {
    private Long id;
}
