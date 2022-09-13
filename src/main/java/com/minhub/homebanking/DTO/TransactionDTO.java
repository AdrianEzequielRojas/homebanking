package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Transaction;
import com.minhub.homebanking.models.TransactionType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class TransactionDTO {

    private long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    private double postTransaction;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.postTransaction = transaction.getPostTransaction();
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {return id;}

    public LocalDateTime getDate() {
        return date;
    }

    public double getPostTransaction() {
        return postTransaction;
    }
}
