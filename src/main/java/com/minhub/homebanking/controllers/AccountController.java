package com.minhub.homebanking.controllers;

import com.minhub.homebanking.DTO.AccountDTO;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.AccountType;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.AccountRepositories;
import com.minhub.homebanking.repositories.ClientRepositories;
import com.minhub.homebanking.services.AccountService;
import com.minhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepositories accountRepositories;

    @Autowired
    private ClientRepositories clientRepositories;


    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @RequestMapping ("/accounts")
    public List<AccountDTO> getAccounts(){
       return accountService.getAllAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }
    @RequestMapping ("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
       return new AccountDTO(accountService.getAccountById(id));
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount (@RequestParam AccountType accountType, Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        List <Account> accountList = client.getAccounts().stream().filter(activeAccount -> activeAccount.isAccountActive()).collect(Collectors.toList());
        if  (accountList.toArray().length >=3){
            return new ResponseEntity<>("you alredy have 3 accounts", HttpStatus.FORBIDDEN);
        }
        else {
            Random randomNumber = new Random();
            Account account = new Account(client, "VIN-"+randomNumber.nextInt(10000000), LocalDateTime.now(),0.00,accountType);
            accountService.saveAccount(account);
            return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
        }
    }
    @PatchMapping("/clients/current/accounts")
    public ResponseEntity <Object> deleteAccount(@RequestParam String number, Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.getClientByNumber(number);
        if(number.isEmpty()){
            return  new ResponseEntity<>("Please select an account", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("This account isn't yours", HttpStatus.FORBIDDEN);
        }
        if(account==null){
            return new ResponseEntity<>("This account doesn't exits", HttpStatus.FORBIDDEN);
        }
        if(account.getBalance()>0){
            return new ResponseEntity<>("this account has money, it cannot be deleted",HttpStatus.FORBIDDEN);
        }
        account.setAccountActive(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Accounts was deleted succesfully",HttpStatus.CREATED);
    }
}
