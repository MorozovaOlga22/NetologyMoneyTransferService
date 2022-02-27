package com.example.moneytransferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseSuccess {
    private final String operationId;
}