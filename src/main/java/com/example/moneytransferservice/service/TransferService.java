package com.example.moneytransferservice.service;

import com.example.moneytransferservice.dto.Operation;
import com.example.moneytransferservice.dto.Transfer;
import com.example.moneytransferservice.repository.AccountsRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Random;

@Service
public class TransferService {
    AccountsRepository accountsRepository;

    private final Random random = new Random();

    public TransferService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public String processTransfer(Transfer transfer) {
        validateCardDate(transfer.getCardFromValidTill());
        final String operationId = String.valueOf(random.nextInt());
        final String operationCode = String.valueOf(random.nextInt());
        accountsRepository.addTransferToAccept(operationId, operationCode, transfer);
        return operationId;
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

    public void confirmOperation(Operation operation) {
        accountsRepository.confirmOperation(operation);
    }
}