package com.minhub.homebanking.models;


import com.minhub.homebanking.DTO.ClientDTO;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    private String name;
    private String lastName;
    private String email;
    private Integer phone;

    private String password;


    public Client() {
    }

    public Client(String name, String lastName, String email,  String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    public Client(String name, String lastName, String email, Integer phone, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Client(String name, String lastName, String email, Account account,Integer phone,  String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.addAccounts(account);
    }

    public Client(String name, String lastName, String email, Integer phone,Account account, Card card, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.addAccounts(account);
        this.addCards(card);
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setemail(String email) {
        this.email = email;
    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {this.clientLoans = clientLoans;}
    public Integer getPhone() {
        return phone;
    }
    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    public void addAccounts(Account account) {
        account.setClient(this);
        accounts.add(account);
    }

    public Set<Card> getCards() {return cards;}
    public void setCards(Set<Card> cards) {this.cards = cards;}

    public void addCards(Card card){
        card.setCard(this);
        cards.add(card);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}