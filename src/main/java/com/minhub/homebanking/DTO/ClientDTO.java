package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
   private String name;
   private String lastName;
   private String email;
   private Integer phone;

   private Set<AccountDTO> accounts;
   private Set<ClientLoanDTO> loans;

   private Set<CardDTO> cards;


    public  ClientDTO (Client client){
        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.loans = client.getClientLoans().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toSet());
        this.cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(Set<ClientLoanDTO> loans) {
        this.loans = loans;
    }

    public long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getLastName() {
      return lastName;
   }

   public String getEmail() {
      return email;
   }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public Integer getPhone() {
        return phone;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
}
