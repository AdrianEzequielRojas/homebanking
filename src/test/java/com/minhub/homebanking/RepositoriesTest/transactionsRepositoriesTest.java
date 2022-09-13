package com.minhub.homebanking.RepositoriesTest;

import com.minhub.homebanking.models.*;
import com.minhub.homebanking.repositories.TransactionRepositories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static com.minhub.homebanking.models.TransactionType.CREDIT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;


import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class transactionsRepositoriesTest {

    @Autowired
    private TransactionRepositories transactionRepositories;


    @Test
    public  void existTransactions(){
        List<Transaction> transactions = transactionRepositories.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void existTransactionsType(){
        List<Transaction> transactions = transactionRepositories.findAll();
        assertThat(transactions, hasItem(hasProperty("type", is(CREDIT))));
    }



}
