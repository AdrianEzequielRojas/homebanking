package com.minhub.homebanking.RepositoriesTest;

import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.ClientRepositories;
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

public class ClientTest {

    @Autowired
    private ClientRepositories clientRepositories;

    @Test
    public void existClients() {
        List<Client> clients = clientRepositories.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void existClientCurrent(){
        List<Client> clients = clientRepositories.findAll();
        assertThat(clients, hasItem(hasProperty("name", is("Melba"))));
    }
}
