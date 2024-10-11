package com.swiss.bank.repository;

import com.swiss.bank.entity.Card;
import com.swiss.bank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumber(String cardNumber);

    Optional<Card> findByUser(UserEntity user);
}
