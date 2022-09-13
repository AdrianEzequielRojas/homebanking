package com.minhub.homebanking.services.implementations;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.repositories.AccountRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements com.minhub.homebanking.services.AccountService {
    @Autowired
    public AccountRepositories accountRepositories;

    @Override
    public List<Account> getAllAccounts(){return accountRepositories.findAll();}

    @Override
    public  Account getAccountById(long id){return accountRepositories.findById(id).get();}

    @Override
    public Account getClientByNumber(String number){return accountRepositories.findByNumber(number);}

    @Override
    public void saveAccount(Account account){accountRepositories.save(account);}
}
