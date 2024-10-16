package com.swiss.bank.service;

import com.swiss.bank.entity.Account;
import com.swiss.bank.entity.Card;
import com.swiss.bank.entity.Purchase;
import com.swiss.bank.exception.BalanceInsuficientException;
import com.swiss.bank.exception.ObjectNotFoundException;
import com.swiss.bank.repository.IAccountRepository;
import com.swiss.bank.repository.IPurchaseRepository;
import com.swiss.bank.web.dto.AccountResponseDto;
import com.swiss.bank.web.dto.PurchaseCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class PurchaseService {

    private final IPurchaseRepository purchaseRepository;
    private final CardService cardService;
    private final IAccountRepository accountRepository;

    public PurchaseService(IPurchaseRepository purchaseRepository, CardService cardService, IAccountRepository accountRepository) {
        this.purchaseRepository = purchaseRepository;
        this.cardService = cardService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Purchase savePurchase(PurchaseCreateDto dto){
        Purchase purchase = new Purchase();

        Card card = cardService.findById(dto.idCard());
        purchase.setCard(card);
        purchase.setValue(dto.value());
        purchase.setDatePurchase(LocalDateTime.now());

        if (dto.typeCard().equals("DEBIT")){
            Account account = accountRepository.findByUser(card.getUser())
                    .orElseThrow(
                            () -> new ObjectNotFoundException(String.format("Account not found. Please check the user ID or username and try again."))
                    );
            if (account.getBalance() < dto.value()){
                throw new BalanceInsuficientException("Insufficient balance to make purchase");
            }
            account.setBalance(account.getBalance() - dto.value());
            accountRepository.save(account);
            purchase.setParcel(1);
            purchaseRepository.save(purchase);
        }else if (dto.typeCard().equals("CREDIT")){
            purchase.setParcel(dto.parcel());
            purchaseRepository.save(purchase);
        }
     return purchase;
    }
}
