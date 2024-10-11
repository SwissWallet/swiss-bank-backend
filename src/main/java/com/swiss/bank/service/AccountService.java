package com.swiss.bank.service;

import com.swiss.bank.entity.Account;
import com.swiss.bank.entity.UserEntity;
import com.swiss.bank.exception.UserUniqueViolationException;
import com.swiss.bank.repository.IAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccount(UserEntity user){
        if (accountRepository.existsByUser(user)){
            throw new UserUniqueViolationException(String.format("User already an account"));
        }
        Account account = new Account();
        account.setAccountNumber(generateUnicNumber());
        account.setUser(user);
        return accountRepository.save(account);
    }

    public String generateUnicNumber(){
        Random random = new Random();
        String accountNumber;
        boolean exists;

        do {
            accountNumber = String.format("%05d", random.nextInt(10000));
            Optional<Account> accountExits = accountRepository.findByAccountNumber(accountNumber);
            exists = accountExits.isPresent();
        }while (exists);
        return accountNumber;
    }

}
