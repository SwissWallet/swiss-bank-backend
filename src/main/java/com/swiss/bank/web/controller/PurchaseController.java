package com.swiss.bank.web.controller;

import com.swiss.bank.entity.Purchase;
import com.swiss.bank.jwt.JwtUserDetails;
import com.swiss.bank.service.PurchaseService;
import com.swiss.bank.web.dto.PurchaseCreateDto;
import com.swiss.bank.web.dto.PurchaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDto> createPurchase(@RequestBody PurchaseCreateDto dto){
        Purchase purchase = purchaseService.savePurchase(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PurchaseResponseDto.toResponse(purchase));
    }

    @PostMapping("/pix")
    public ResponseEntity<String> createPurchasePix(@RequestBody PurchaseCreateDto dto){
        String codePix = purchaseService.generatePurchasePix(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(codePix);
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<PurchaseResponseDto>> listCurrentPurchases(@AuthenticationPrincipal JwtUserDetails userDetails){
        List<Purchase> purchases = purchaseService.listCurrentPurchases(userDetails.getId());
        return ResponseEntity.ok().body(PurchaseResponseDto.toListResponse(purchases));
    }

    @GetMapping("/paids")
    public ResponseEntity<List<PurchaseResponseDto>> listPaidsPurchases(){
        List<Purchase> purchases = purchaseService.listPaidsPurchases();
        return ResponseEntity.ok().body(PurchaseResponseDto.toListResponse(purchases));
    }

    @PutMapping("/paids/deposit")
    public ResponseEntity<PurchaseResponseDto> updateStatus(@RequestParam Long id){
        Purchase purchase = purchaseService.updateStatus(id);
        return ResponseEntity.ok().body(PurchaseResponseDto.toResponse(purchase));
    }

}
