package com.minhub.homebanking.services;

import com.minhub.homebanking.models.Account;

import java.util.Collection;
import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();

    Account getAccountById(long id);

    Account getClientByNumber(String number);

    void saveAccount(Account account);


}
