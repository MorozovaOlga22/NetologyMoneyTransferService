package com.example.moneytransferservice.repository;

import com.example.moneytransferservice.dto.Card;
import com.example.moneytransferservice.dto.Operation;
import com.example.moneytransferservice.dto.Transfer;
import com.example.moneytransferservice.dto.TransferToAccept;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountsRepository {
    private final Map<String, Card> userCardsMap = createTestCardsMap();
    private final Map<String, TransferToAccept> transfersToAccept = new HashMap<>();

    public synchronized void addTransferToAccept(String operationId, String operationCode, Transfer transfer) {
        checkCards(transfer);
        transfersToAccept.put(operationId, new TransferToAccept(transfer, operationCode));
    }

    public synchronized void confirmOperation(Operation operation) {
        final String operationId = operation.getOperationId();
        final TransferToAccept transferToAccept = transfersToAccept.get(operationId);
        checkOperation(operation, transferToAccept);

        final Transfer transfer = transferToAccept.getTransfer();
        final Integer amount = transfer.getAmount().getValue();
        writeOffMoney(transfer, amount);

        transfersToAccept.remove(operationId);
    }

    /*Вспомогательные методы*/

    private Map<String, Card> createTestCardsMap() {
        final Map<String, Card> cardsMap = new HashMap<>();
        cardsMap.put("1111111111111111", new Card("1111111111111111", "07/22", "111", 1_000_000));
        cardsMap.put("2222222222222222", new Card("2222222222222222", "05/25", "222", 5_000));
        cardsMap.put("3333333333333333", new Card("3333333333333333", "01/30", "333", 10));

        return cardsMap;
    }

    private void checkCards(Transfer transfer) {
        final Card cardFrom = userCardsMap.get(transfer.getCardFromNumber());
        if (cardFrom == null) {
            throw new RuntimeException("Write-off card not found");
        }
        compareCardFromData(cardFrom, transfer);

        if (!userCardsMap.containsKey(transfer.getCardToNumber())) {
            throw new RuntimeException("Recipient's card not found");
        }
    }

    private void checkOperation(Operation operation, TransferToAccept transferToAccept) {
        if (transferToAccept == null) {
            throw new RuntimeException("Operation not found");
        }
        if (!operation.getCode().equals(transferToAccept.getOperationCode())) {
            throw new RuntimeException("Incorrect operation code");
        }
        checkCards(transferToAccept.getTransfer());
    }

    private void compareCardFromData(Card cardFrom, Transfer transfer) {
        if (!cardFrom.getCardFromCVV().equals(transfer.getCardFromCVV()) ||
                !cardFrom.getCardFromValidTill().equals(transfer.getCardFromValidTill())) {
            throw new RuntimeException("Incorrect data");
        }
        if (cardFrom.getAccountBalance() < transfer.getAmount().getValue()) {
            throw new RuntimeException("Insufficient funds");
        }
    }

    private void writeOffMoney(Transfer transfer, Integer amount) {
        correctBalance(transfer.getCardFromNumber(), amount * (-1));
        correctBalance(transfer.getCardToNumber(), amount);
    }

    private void correctBalance(String cardNumber, Integer amount) {
        final Card card = userCardsMap.get(cardNumber);
        card.setAccountBalance(card.getAccountBalance() + amount);
    }
}