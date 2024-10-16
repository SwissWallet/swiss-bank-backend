package com.swiss.bank.service;

import com.swiss.bank.repository.IPurchaseRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final IPurchaseRepository purchaseRepository;

    public PurchaseService(IPurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
}
