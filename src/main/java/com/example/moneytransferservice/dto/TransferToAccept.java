package com.example.moneytransferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferToAccept {
    private final Transfer transfer;
    private final String operationCode;
}