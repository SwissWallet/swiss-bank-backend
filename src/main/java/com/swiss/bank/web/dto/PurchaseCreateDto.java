package com.swiss.bank.web.dto;

import com.swiss.bank.entity.Card;

import java.time.LocalDateTime;

public record PurchaseCreateDto(String username,
                                String typePayment,
                                float value) {
}
