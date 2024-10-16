package com.swiss.bank.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swiss.bank.entity.Purchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PurchaseResponseDto(Long id,
                                  CardResponseDto card,
                                  float value,
                                  @JsonFormat(pattern = "dd/MM/yyyy")
                                  LocalDateTime datePurchase,
                                  int parcel) {

    public static PurchaseResponseDto toResponse(Purchase purchase){
        return new PurchaseResponseDto(
                purchase.getId(),
                CardResponseDto.toResponse(purchase.getCard()),
                purchase.getValue(),
                purchase.getDatePurchase(),
                purchase.getParcel()
        );
    }

    public static List<PurchaseResponseDto> toListResponse(List<Purchase> purchases){
        return purchases.stream()
                .map(purchase -> toResponse(purchase)).collect(Collectors.toList());
    }

}
