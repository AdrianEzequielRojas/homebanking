package com.minhub.homebanking.services.implementations;

import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.repositories.CardRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService implements com.minhub.homebanking.services.CardService {

    @Autowired
    CardRepositories cardRepositories;

    @Override
    public Card getCardByNumber(String number){
        return cardRepositories.findByNumber(number);
    }
    @Override
    public  void saveCard(Card card){cardRepositories.save(card);}
}
