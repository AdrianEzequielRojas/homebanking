package com.minhub.homebanking.services;

import com.minhub.homebanking.models.Card;

public interface CardService {
    Card getCardByNumber(String number);

    void saveCard(Card card);
}
