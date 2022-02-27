package com.example.moneytransferservice.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferControllerTest {

    @Test
    void validateCardDate() {
        final String cardFromValidTill = "11/25";
        final int currentMonth = 2;
        final int currentYear = 2022;

        TransferController.validateCardDate(cardFromValidTill, currentMonth, currentYear);
    }

    @Test
    void validateCardDateOldYear() {
        final String cardFromValidTill = "11/21";
        final int currentMonth = 2;
        final int currentYear = 2022;

        assertThrows(RuntimeException.class, () -> TransferController.validateCardDate(cardFromValidTill, currentMonth, currentYear));
    }

    @Test
    void validateCardDateOldMonth() {
        final String cardFromValidTill = "01/22";
        final int currentMonth = 2;
        final int currentYear = 2022;

        assertThrows(RuntimeException.class, () -> TransferController.validateCardDate(cardFromValidTill, currentMonth, currentYear));
    }
}