package com.swiss.bank.service;

import com.swiss.bank.entity.*;
import com.swiss.bank.exception.BalanceInsuficientException;
import com.swiss.bank.exception.ObjectNotFoundException;
import com.swiss.bank.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CodePixService {

    private final ICodeRepository codeRepository;
    private final IAccountRepository accountRepository;
    private final IUserRepository userRepository;
    private final IPurchaseRepository purchaseRepository;
    private final IExtractRepository extractRepository;

    public CodePixService(ICodeRepository codeRepository, IAccountRepository accountRepository, IUserRepository userRepository, IPurchaseRepository purchaseRepository, IExtractRepository extractRepository) {
        this.codeRepository = codeRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
        this.extractRepository = extractRepository;
    }

    @Transactional
    public String createCodePix(float value){
        CodePix codePix = new CodePix();
        codePix.setValue(value);
        codePix.setCode(generateUniqueCode());
        codePix = codeRepository.save(codePix);
        return codePix.getCode();
    }

    public String generateUniqueCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder uniqueString = new StringBuilder(30);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        boolean exists;

        do {
            uniqueString.setLength(0);
            for (int i = 0; i < 30; i++) {
                int index = random.nextInt(characters.length());
                uniqueString.append(characters.charAt(index));
            }

            Optional<CodePix> codeExists = codeRepository.findCodePixByCode(uniqueString.toString());
            exists = codeExists.isPresent();
        } while (exists);

        return uniqueString.toString();
    }

    @Transactional
    public void paymentCodePix(String code, Long idUser){
        UserEntity user = userRepository.findById(idUser)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );
        Account account = accountRepository.findByUser(user)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Account not found. Please check the user ID or username and try again."))
                );
        Account accountAdmin = accountRepository.findByAccountNumber("12345")
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Account not found. Please check the user ID or username and try again."))
                );
        CodePix codePix = codeRepository.findCodePixByCode(code)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Code pix not found. Please check the user ID or username and try again."))
                );
        if (account.getBalance() < codePix.getValue()){
            throw new BalanceInsuficientException("Insufficient balance to make payment");
        }

        Purchase purchase = purchaseRepository.findByCodePix(code);
        purchase.setStatus(StatusPurhcase.PAID);
        accountAdmin.setBalance(accountAdmin.getBalance() + codePix.getValue());
        account.setBalance(account.getBalance() - codePix.getValue());
        accountRepository.save(account);
        accountRepository.save(accountAdmin);

        Extract extract = new Extract();
        extract.setAccount(account);
        extract.setValue((double) purchase.getValue());
        extract.setType(Extract.Type.TRANSACTION);
        extract.setDescription(String.format("Purchase payment made by pix"));
        extract.setDate(LocalDateTime.now());
        extractRepository.save(extract);

        extract.setAccount(accountAdmin);
        extract.setValue((double) purchase.getValue());
        extract.setType(Extract.Type.DEPOSIT);
        extract.setDescription("Purchase deposit made by pix");
        extract.setDate(LocalDateTime.now());
        extractRepository.save(extract);

        codeRepository.deleteById(codePix.getId());
    }
}
