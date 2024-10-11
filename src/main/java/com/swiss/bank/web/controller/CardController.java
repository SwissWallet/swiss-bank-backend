package com.swiss.bank.web.controller;

import com.swiss.bank.entity.Card;
import com.swiss.bank.jwt.JwtUserDetails;
import com.swiss.bank.service.CardService;
import com.swiss.bank.web.dto.CardResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CardResponseDto> getCardByUser(@AuthenticationPrincipal JwtUserDetails userDetails){
        Card card = cardService.findById(userDetails.getId());
        return ResponseEntity.ok().body(CardResponseDto.toResponse(card));
    }
}
