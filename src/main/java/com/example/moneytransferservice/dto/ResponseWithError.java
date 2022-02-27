package com.example.moneytransferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWithError {
    private final String message;
    private final int id;
}