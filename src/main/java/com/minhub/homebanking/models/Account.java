package com.minhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany (mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;


    private String number;
    private LocalDateTime creationDate;
    private double balance;

    private boolean accountActive;

    private AccountType accountType;

    public Account (){}



    public Account(Client client, String number, LocalDateTime creationDate, double balance, AccountType accountType) {
        this.client = client;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.accountActive = true;
        this.accountType = accountType;
    }

    public Account(String number, LocalDateTime date, double balance,  AccountType accountType) {
        this.number = number;
        this.creationDate = date;
        this.balance = balance;
        this.accountActive = true;
        this.accountType = accountType;
    }
    public Account(String number, LocalDateTime date, double balance, Transaction transactions, AccountType accountType) {
        this.number = number;
        this.creationDate = date;
        this.balance = balance;
        this.addTransaction(transactions);
        this.accountActive = true;
        this.accountType = accountType;
    }


    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }


    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}