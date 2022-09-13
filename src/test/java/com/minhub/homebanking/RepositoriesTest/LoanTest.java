package com.minhub.homebanking.RepositoriesTest;

import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.Loan;
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

public class LoanTest {
    @Autowired
    public LoanRepositories loanRepositories;

    @Test
    public void existLoans() {
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, is(not(empty())));
    }

    @Test
    public void existPersonalLoan() {
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }


}
