package com.swiss.bank.service;

import com.swiss.bank.repository.ICardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final ICardRepository cardRepository;

    public CardService(ICardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
}
