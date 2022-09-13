package com.minhub.homebanking.services.implementations;

import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.ClientRepositories;
import com.minhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServices implements ClientService {

    @Autowired
    ClientRepositories clientRepositories;

    @Override
    public List<Client> getAllClients(){return clientRepositories.findAll();}

    @Override
    public Client getClientById(long id){
        return clientRepositories.findById(id).get();
    }
    @Override
    public Client findClientByEmail(String email){
        return clientRepositories.findByEmail(email);
    }
    @Override
    public void saveClient(Client client){
        clientRepositories.save(client);
    }
}
