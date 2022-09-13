package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Card;

import com.minhub.homebanking.models.Transaction;

import java.time.LocalDate;

public class PaymentApplicationDTO {

    private long id;
    private String cardHolder;
    private String accountNumber;
    private String cardNumber;
    private String description;
    private int cvv;
    private double amount;
    private LocalDate thruDate;

    public PaymentApplicationDTO() {
    }

    public PaymentApplicationDTO(Account account, Card card, Transaction transaction) {
        this.cardNumber = card.getNumber();
        this.cvv = card.getCvv();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.thruDate = card.getThruDate();
        this.cardHolder = card.getCardholder();
        this.accountNumber = account.getNumber();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}