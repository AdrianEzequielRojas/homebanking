package com.minhub.homebanking.controllers;

import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.CardColor;
import com.minhub.homebanking.models.CardType;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.CardRepositories;
import com.minhub.homebanking.repositories.ClientRepositories;
import com.minhub.homebanking.services.ClientService;
import com.minhub.homebanking.services.implementations.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientRepositories clientRepositories;
    @Autowired
    private CardRepositories cardRepositories;

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;


    @PostMapping( "/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardColor cardColor, @RequestParam CardType cardType, Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        List<Card> cardListActive = client.getCards().stream().filter(card -> card.isCardActive()).collect(Collectors.toList());
        if (cardListActive.stream().filter(card -> card.getCardType().equals(cardType)).count() > 2) {
            return new ResponseEntity<>("Clients can only have 3 cards", HttpStatus.FORBIDDEN);
        }
        if (cardListActive.stream().filter(card -> card.getCardType().equals(cardType)).filter(card -> card.getCardColor().equals(cardColor)).count() > 0) {
            return new ResponseEntity<>("you can only have 1 type, try another", HttpStatus.FORBIDDEN);
        } else {
            int cvvRandomNumber = getRandomNumber(100, 999);
            String randomCardNumber = getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999);
            while (cardService.getCardByNumber(randomCardNumber) != null) {
                randomCardNumber = getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999);
            }
            Card card = new Card(client, cardType, cardColor, randomCardNumber, cvvRandomNumber, LocalDate.now(), LocalDate.now().plusYears(5));
            cardService.saveCard(card);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

   @PatchMapping ("/clients/current/cards")
        public ResponseEntity <Object> deleteCard(
                @RequestParam String number, Authentication authentication){
            Client client = clientService.findClientByEmail(authentication.getName());
            Card card = cardService.getCardByNumber(number);
            if(number.isEmpty()){
                return new ResponseEntity<>("Please select a card", HttpStatus.FORBIDDEN);

        } if(!client.getCards().contains(card)){
                return new ResponseEntity<>("This card isn't yours",HttpStatus.FORBIDDEN);

       }card.setCardActive(false);
            cardService.saveCard(card);
            return new ResponseEntity<>("Card was deleted", HttpStatus.CREATED);
    }



}


