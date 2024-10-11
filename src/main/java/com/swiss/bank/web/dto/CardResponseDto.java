package com.swiss.bank.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swiss.bank.entity.Card;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CardResponseDto(UserResponseDto user,
                              String cardNumber,
                              @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
                              LocalDateTime validity,
                              String cvv,
                              float cardLimit) {

    public static CardResponseDto toResponse(Card card){
        return new CardResponseDto(
                UserResponseDto.toUserResponse(card.getUser()),
                card.getCardNumber(),
                card.getValidity(),
                card.getCvv(),
                card.getCardLimit()
        );
    }

    public static List<CardResponseDto> toListResponse(List<Card> cards){
        return cards.stream()
                .map(card -> toResponse(card)).collect(Collectors.toList());
    }
}
