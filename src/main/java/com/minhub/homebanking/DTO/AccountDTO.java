package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.AccountType;
import com.minhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;

    private Set<TransactionDTO> transaction;
    private boolean accountActive;

    private AccountType accountType;

    public AccountDTO (){}

    public AccountDTO(Account account){
       this.id = account.getId();
       this.number = account.getNumber();
       this.creationDate = account.getCreationDate();
       this.balance = account.getBalance();
       this.transaction = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
       this.accountActive = account.isAccountActive();
       this.accountType = account.getAccountType();
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<TransactionDTO> getTransaction() {
        return transaction;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public double getBalance() {
        return balance;
    }
}
