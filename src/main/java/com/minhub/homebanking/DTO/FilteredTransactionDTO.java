package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.Transaction;

import java.time.LocalDateTime;

public class FilteredTransactionDTO {

    public LocalDateTime fromDate;

    public LocalDateTime thruDate;

    public String accountNumber;

    public FilteredTransactionDTO (){}

    public FilteredTransactionDTO (LocalDateTime fromDate, LocalDateTime thruDate, String accountNumber){
        this.fromDate =  fromDate;
        this.thruDate = thruDate;
        this.accountNumber = accountNumber;

    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
