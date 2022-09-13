package com.minhub.homebanking.services;

import com.minhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    public Loan findByName(String name);

    public List<Loan> getAllLoans();
}
