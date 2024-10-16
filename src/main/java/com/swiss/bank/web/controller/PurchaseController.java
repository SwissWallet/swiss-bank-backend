package com.swiss.bank.web.controller;

import com.swiss.bank.entity.Purchase;
import com.swiss.bank.service.PurchaseService;
import com.swiss.bank.web.dto.PurchaseCreateDto;
import com.swiss.bank.web.dto.PurchaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<PurchaseResponseDto> createPurchase(@RequestBody PurchaseCreateDto dto){
        Purchase purchase = purchaseService.savePurchase(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PurchaseResponseDto.toResponse(purchase));
    }
}
