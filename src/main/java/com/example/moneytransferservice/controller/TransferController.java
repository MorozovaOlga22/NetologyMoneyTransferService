package com.example.moneytransferservice.controller;

import com.example.moneytransferservice.dto.Operation;
import com.example.moneytransferservice.dto.ResponseSuccess;
import com.example.moneytransferservice.dto.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Random;

@CrossOrigin
@RestController
public class TransferController {
    private final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final Random random = new Random();

    @PostMapping("/transfer")
    public ResponseSuccess transfer(@Valid @RequestBody Transfer transfer) {
        validateCardDate(transfer.getCardFromValidTill());
        logger.info(transfer.toString());
        return new ResponseSuccess(String.valueOf(random.nextInt()));
    }

    @PostMapping("/confirmOperation")
    public ResponseSuccess confirmOperation(@RequestBody Operation operation) {
        logger.info(operation.toString());
        return new ResponseSuccess("Success");
    }

    private void validateCardDate(String cardFromValidTill) {
        final Calendar calendar = Calendar.getInstance();
        final int currentMonth = calendar.get(Calendar.MONTH) + 1;
        final int currentYear = calendar.get(Calendar.YEAR);

        validateCardDate(cardFromValidTill, currentMonth, currentYear);
    }

    static void validateCardDate(String cardFromValidTill, int currentMonth, int currentYear) {
        final String[] cardYearAndMonth = cardFromValidTill.split("/");
        final int cardMonth = Integer.parseInt(cardYearAndMonth[0]);
        final int cardYear = 2000 + Integer.parseInt(cardYearAndMonth[1]);

        if (currentYear > cardYear || currentYear == cardYear && currentMonth > cardMonth) {
            throw new RuntimeException("Card expired");
        }
    }
}
