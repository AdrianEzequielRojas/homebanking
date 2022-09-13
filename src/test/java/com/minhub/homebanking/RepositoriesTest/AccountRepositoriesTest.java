package com.minhub.homebanking.RepositoriesTest;



import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.Loan;
import com.minhub.homebanking.repositories.AccountRepositories;
import com.minhub.homebanking.repositories.LoanRepositories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class AccountRepositoriesTest {

    @Autowired
    public AccountRepositories accountRepositories;

    @Test
    public  void existAccount(){
        List<Account> accounts = accountRepositories.findAll();
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void existAccountsNumber(){
        List<Account> accounts = accountRepositories.findAll();
        assertThat(accounts, hasItem(hasProperty("number", is("VIN001"))));
    }
}
