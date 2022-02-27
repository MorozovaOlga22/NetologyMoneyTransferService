package com.example.moneytransferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@Validated
public class Transfer {
    public static final String CARD_NUMBER_REGEX = "^\\d{16}$";
    public static final String CARD_NUMBER_ERROR_TEXT = "Card number must contain 16 numbers";

    @Pattern(regexp = CARD_NUMBER_REGEX, message = CARD_NUMBER_ERROR_TEXT)
    private final String cardFromNumber;

    @Pattern(regexp = "^(0?[1-9]|1[012])/(\\d\\d)$", message = "Card date must be in format \"MM/YY\"")
    private final String cardFromValidTill;

    @Pattern(regexp = "^\\d{3}$", message = "CVV must contain 3 numbers")
    private final String cardFromCVV;

    @Pattern(regexp = CARD_NUMBER_REGEX, message = CARD_NUMBER_ERROR_TEXT)
    private final String cardToNumber;

    @Valid
    private final Amount amount;
}