package com.minhub.homebanking.services.implementations;

import com.minhub.homebanking.models.Loan;
import com.minhub.homebanking.repositories.LoanRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService implements com.minhub.homebanking.services.LoanService {
    @Autowired
    public LoanRepositories loanRepositories;

    @Override
    public Loan findByName(String name){return loanRepositories.findByName(name);}

    @Override
    public List<Loan> getAllLoans(){
        return loanRepositories.findAll();
    }

}
