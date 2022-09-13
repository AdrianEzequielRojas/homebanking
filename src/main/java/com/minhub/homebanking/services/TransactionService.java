package com.minhub.homebanking.services;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.Set;

public interface TransactionService {
    void saveTransactions(Transaction transaction);

    public Set<Transaction> filterTransactionsWithDate(LocalDateTime fromDate, LocalDateTime thruDate, Account account);
}
