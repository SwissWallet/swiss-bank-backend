package com.swiss.bank.web.dto;

import com.swiss.bank.entity.Card;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CardResponseDto(UserResponseDto user,
                              String cardNumber,
                              LocalDateTime validity,
                              Long cvv,
                              float limit) {

    public static CardResponseDto toUserResponse(Card card){
        return new CardResponseDto(
                UserResponseDto.toUserResponse(card.getUser()),
                card.getCardNumber(),
                card.getValidity(),
                card.getCvv(),
                card.getLimit()
        );
    }

    public static List<CardResponseDto> toListUserResponse(List<Card> cards){
        return cards.stream()
                .map(card -> toUserResponse(card)).collect(Collectors.toList());
    }
}
