package com.minhub.homebanking.services.implementations;

import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.repositories.ClientLoanRepositories;
import com.minhub.homebanking.services.ClientLoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanService implements ClientLoanServices {

     @Autowired
    ClientLoanRepositories clientLoanRepositories;

    @Override
    public void saveClientLoan(ClientLoan clientLoan){
        clientLoanRepositories.save(clientLoan);
    }

}
