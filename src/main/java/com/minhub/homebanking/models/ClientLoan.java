package com.minhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    private int payment;
    private double amount;


    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loans;


    public ClientLoan (){}


    public ClientLoan(Client client, int payment, double amount, Loan loans) {
        this.client = client;
        this.payment = payment;
        this.amount = amount;
        this.loans = loans;

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Loan getLoans() {
        return loans;
    }

    public void setLoans(Loan loans) {
        this.loans = loans;
    }


}
