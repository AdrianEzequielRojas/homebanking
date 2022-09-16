package com.minhub.homebanking.controllers;

import com.minhub.homebanking.DTO.ClientDTO;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.ClientRepositories;
import com.minhub.homebanking.services.AccountService;
import com.minhub.homebanking.services.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepositories clientRepositories;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;


    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getAllClients().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String name, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(
                    "you must fill in the fields", HttpStatus.FORBIDDEN);
        }
        if (clientService.findClientByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(name, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
    return  new ClientDTO(clientService.findClientByEmail(authentication.getName()));
    }
}

