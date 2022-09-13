
package com.minhub.homebanking.RepositoriesTest;

import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.CardRepositories;
import com.minhub.homebanking.repositories.ClientRepositories;
import com.minhub.homebanking.services.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class cardRepositoriesTest {

    @Autowired
    private CardRepositories cardRepositories;

    @Test
    public void ExitsCard(){
        List<Card> cards = cardRepositories.findAll();
        assertThat(cards, is(not(empty())));

    }
    @Test
    public void existCardGName(){
        List<Card> cards = cardRepositories.findAll();
        assertThat(cards, hasItem(hasProperty("cardholder", is("Melba Morel"))));
    }
}
