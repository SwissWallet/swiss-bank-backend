package com.swiss.bank.service;

import com.swiss.bank.entity.*;
import com.swiss.bank.exception.BalanceInsuficientException;
import com.swiss.bank.exception.ObjectNotFoundException;
import com.swiss.bank.repository.*;
import com.swiss.bank.web.dto.AccountResponseDto;
import com.swiss.bank.web.dto.PurchaseCreateDto;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class PurchaseService {

    private final IPurchaseRepository purchaseRepository;
    private final ICardRepository cardRepository;
    private final IAccountRepository accountRepository;
    private final IUserRepository userRepository;
    private final CodePixService codePixService;
    private final IExtractRepository extractRepository;

    public PurchaseService(IPurchaseRepository purchaseRepository, ICardRepository cardRepository, IAccountRepository accountRepository, IUserRepository userRepository, CodePixService codePixService, IExtractRepository extractRepository) {
        this.purchaseRepository = purchaseRepository;
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.codePixService = codePixService;
        this.extractRepository = extractRepository;
    }

    @Transactional
    public Purchase savePurchase(PurchaseCreateDto dto) {
        Purchase purchase = new Purchase();
        UserEntity user = userRepository.findByUsername(dto.username())
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );
        Card card = cardRepository.findByUser(user)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Card not found. Please check the user ID or username and try again."))
                );
        Account accountAdmin = accountRepository.findByAccountNumber("12345")
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Account not found. Please check the user ID or username and try again."))
                );
        Account account = accountRepository.findByUser(user)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Account not found. Please check the user ID or username and try again."))
                );
        purchase.setUser(user);
        purchase.setValue(dto.value());
        purchase.setDatePurchase(LocalDateTime.now());

        if (dto.typePayment().equals("DEBIT")) {

            if (account.getBalance() < dto.value()) {
                throw new BalanceInsuficientException("Insufficient balance to make purchase");
            }
            account.setBalance(account.getBalance() - dto.value());
            accountRepository.save(account);

            purchase.setStatus(StatusPurhcase.DEPOSIT);
            purchaseRepository.save(purchase);
        } else if (dto.typePayment().equals("CREDIT")) {
            if (card.getCardLimit() < dto.value()) {
                throw new BalanceInsuficientException("Insufficient limit to make purchase");
            }
            card.setCardLimit(card.getCardLimit() - dto.value());
            cardRepository.save(card);
            purchase.setStatus(StatusPurhcase.DEPOSIT);
            purchaseRepository.save(purchase);
        }
        accountAdmin.setBalance(accountAdmin.getBalance() + dto.value());
        accountRepository.save(accountAdmin);

        Extract extractUser = new Extract();
        extractUser.setAccount(account);
        extractUser.setValue((double) purchase.getValue());
        extractUser.setType(Extract.Type.TRANSACTION);
        extractUser.setDescription(String.format("Purchase payment made by %s", dto.typePayment()));
        extractUser.setDate(LocalDateTime.now());
        extractRepository.save(extractUser);

        Extract extractAdmin = new Extract();
        extractAdmin.setAccount(accountAdmin);
        extractAdmin.setValue((double) purchase.getValue());
        extractAdmin.setType(Extract.Type.DEPOSIT);
        extractAdmin.setDescription("Purchase deposit made " + dto.typePayment());
        extractAdmin.setDate(LocalDateTime.now());
        extractRepository.save(extractAdmin);

        return purchase;
    }

    @Transactional
    public String generatePurchasePix(PurchaseCreateDto dto){
        String code = codePixService.createCodePix(dto.value());
        UserEntity user = userRepository.findByUsername(dto.username())
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );
        Purchase purchase = new Purchase();
        purchase.setDatePurchase(LocalDateTime.now());
        purchase.setValue(dto.value());
        purchase.setUser(user);
        purchase.setCodePix(code);
        purchaseRepository.save(purchase);
        return code;
    }

    @Transactional(readOnly = true)
    public List<Purchase> listCurrentPurchases(Long idUser){
        UserEntity user = userRepository.findById(idUser)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );;
        return purchaseRepository.findAllByUser(user);
    }

    public void deleteByUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );
        purchaseRepository.deleteByUser(user);
    }

    public List<Purchase> listPaidsPurchases() {
        return purchaseRepository.findAllByStatus(StatusPurhcase.PAID);
    }

    public Purchase updateStatus(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Purchase not found. Please check the user ID or username and try again."))
                );
        purchase.setStatus(StatusPurhcase.DEPOSIT);
        return purchaseRepository.save(purchase);
    }
}
