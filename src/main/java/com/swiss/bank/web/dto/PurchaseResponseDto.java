package com.swiss.bank.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swiss.bank.entity.Purchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PurchaseResponseDto(Long id,
                                  UserResponseDto user,
                                  float value,
                                  @JsonFormat(pattern = "dd/MM/yyyy")
                                  LocalDateTime datePurchase,
                                  String status) {

    public static PurchaseResponseDto toResponse(Purchase purchase){
        return new PurchaseResponseDto(
                purchase.getId(),
                UserResponseDto.toUserResponse(purchase.getUser()),
                purchase.getValue(),
                purchase.getDatePurchase(),
                purchase.getStatus().name()
        );
    }

    public static List<PurchaseResponseDto> toListResponse(List<Purchase> purchases){
        return purchases.stream()
                .map(purchase -> toResponse(purchase)).collect(Collectors.toList());
    }

}
