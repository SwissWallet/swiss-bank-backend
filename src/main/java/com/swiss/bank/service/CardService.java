package com.swiss.bank.service;

import com.swiss.bank.entity.Card;
import com.swiss.bank.entity.UserEntity;
import com.swiss.bank.repository.ICardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CardService {

    private final ICardRepository cardRepository;

    public CardService(ICardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Card saveCard(UserEntity user){
        Card card = new Card();
        card.setCardNumber(generateUniqueNumber());
        card.setCvv(generateCvvNumber());
        card.setUser(user);
        card.setLimit(200);
        card.setValidity(LocalDateTime.now().plusMonths(5));
        return cardRepository.save(card);
    }

    public String generateUniqueNumber() {
        Random random = new Random();
        String cardNumber;
        boolean exists;

        do {
            long number = Math.abs(random.nextLong() % 10000000000000000L);
            cardNumber = String.format("%016d", number);
            Optional<Card> cardExists = cardRepository.findByCardNumber(cardNumber);
            exists = cardExists.isPresent();
        } while (exists);

        return cardNumber;
    }

    public Long generateCvvNumber(){
        Random random = new Random();
        String cvvNumber = String.format("%03d", random.nextInt(1000));
        return Long.valueOf(cvvNumber);
    }
}
