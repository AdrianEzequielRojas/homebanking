package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private long id;
    private double amount;
    private List<Integer> payments;
    private String nameLoan;

    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.amount = loan.getMaxAmount();
        this.payments =  loan.getPayments();
        this.nameLoan = loan.getName();
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return nameLoan;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
