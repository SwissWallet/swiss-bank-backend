package com.swiss.bank.service;

import com.swiss.bank.entity.Card;
import com.swiss.bank.entity.UserEntity;
import com.swiss.bank.exception.ObjectNotFoundException;
import com.swiss.bank.repository.ICardRepository;
import com.swiss.bank.repository.IUserRepository;
import com.swiss.bank.web.dto.UserResponseDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CardService {

    private final ICardRepository cardRepository;
    private final IUserRepository userRepository;

    public CardService(ICardRepository cardRepository, IUserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Card saveCard(UserEntity user){
        Card card = new Card();
        card.setCardNumber(generateUniqueNumber());
        card.setCvv(generateCvvNumber());
        card.setUser(user);
        card.setCardLimit(200);
        card.setValidity(LocalDateTime.now().plusMonths(5));
        return cardRepository.save(card);
    }

    public String generateUniqueNumber() {
        Random random = new Random();
        String cardNumber;
        boolean exists;

        do {
            int number = random.nextInt(100000000);
            cardNumber = String.format("%08d", number);

            Optional<Card> cardExists = cardRepository.findByCardNumber(cardNumber);
            exists = cardExists.isPresent();
        } while (exists);

        return cardNumber;
    }

    public String generateCvvNumber() {
        Random random = new Random();
        return String.format("%03d", random.nextInt(1000));
    }

    @Transactional(readOnly = true)
    public Card findById(Long id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );
        return cardRepository.findByUser(user).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Card not found. Please check the user ID or username and try again."))
        );
    }

    public void deleteByUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );
        cardRepository.deleteByUser(user);
    }
}
