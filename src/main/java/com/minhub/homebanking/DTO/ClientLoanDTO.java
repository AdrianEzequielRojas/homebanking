package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.ClientLoan;

import java.util.Set;

public class ClientLoanDTO {

    private long Id;
    private long loanId;
    private String nameLoan;
    private int amountsRequest;
    private int paymentsRequest;



    public ClientLoanDTO(){}
    public ClientLoanDTO(ClientLoan clientLoan){
        this.Id = clientLoan.getId();
        this.loanId = clientLoan.getLoans().getId();
        this.nameLoan = clientLoan.getLoans().getName();
        this.amountsRequest = (int) clientLoan.getAmount();
        this.paymentsRequest = (int) clientLoan.getPayment();

    }

    public long getId() {
        return Id;
    }
    public long getLoanId() {
        return loanId;
    }
    public String getNameLoan() {
        return nameLoan;
    }
    public int getAmountsRequest() {
        return amountsRequest;
    }
    public int getPaymentsRequest() {return paymentsRequest;}


}
