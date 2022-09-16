package com.minhub.homebanking.services.implementations;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Transaction;
import com.minhub.homebanking.repositories.TransactionRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService implements com.minhub.homebanking.services.TransactionService {
    @Autowired
    public TransactionRepositories transactionRepositories;

    @Override
    public void saveTransactions(Transaction transaction){
        transactionRepositories.save(transaction);
    }

    @Override
    public Set<Transaction> filterTransactionsWithDate(LocalDateTime fromDate, LocalDateTime thruDate, Account account){
        return  transactionRepositories.findByDateBetween(fromDate,thruDate).stream().filter(transaction -> transaction.getAccount()==account).collect(Collectors.toSet());
    }
}
