package com.swiss.bank.web.dto;


public record PurchaseCreateDto(String username,
                                String typePayment,
                                float value) {
}
