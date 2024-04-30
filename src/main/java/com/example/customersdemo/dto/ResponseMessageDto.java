package com.example.customersdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMessageDto {
    private int statusCode;

    private String message;
}
