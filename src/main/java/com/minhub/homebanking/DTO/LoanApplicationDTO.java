package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.models.Loan;

public class LoanApplicationDTO {


    private long id;
    private String nameLoan;
    private double amount;
    private int payments;
    private String accountNumberDestiny;



    public LoanApplicationDTO(){}


    public LoanApplicationDTO(ClientLoan clientLoan, Account account, Loan loan){
        this.id = loan.getId();
        this.nameLoan = loan.getName();
        this.amount = clientLoan.getAmount();
        this.payments = (int) clientLoan.getPayment();
        this.accountNumberDestiny = account.getNumber();

    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getAccountNumberDestiny() {
        return accountNumberDestiny;
    }

    public String getNameLoan() {
        return nameLoan;
    }
}
