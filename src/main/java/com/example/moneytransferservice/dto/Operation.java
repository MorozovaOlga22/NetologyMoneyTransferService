package com.example.moneytransferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Operation {
    private final String operationId;
    private final String code;
}