package com.swiss.bank.repository;

import com.swiss.bank.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICardRepository extends JpaRepository<Card, Long> {
}
