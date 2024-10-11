package com.swiss.bank.service;

import com.swiss.bank.entity.Account;
import com.swiss.bank.entity.UserEntity;
import com.swiss.bank.exception.ObjectNotFoundException;
import com.swiss.bank.exception.UserUniqueViolationException;
import com.swiss.bank.repository.IAccountRepository;
import com.swiss.bank.repository.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;
    private final IUserRepository userRepository;

    public AccountService(IAccountRepository accountRepository, IUserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Account createAccount(UserEntity user){
        if (accountRepository.existsByUser(user)){
            throw new UserUniqueViolationException(String.format("User already an account"));
        }
        Account account = new Account();
        account.setAccountNumber(generateUniqueNumber());
        account.setUser(user);
        return accountRepository.save(account);
    }

    public String generateUniqueNumber(){
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

    @Transactional(readOnly = true)
    public  Account findByUser(Long idUser){
        UserEntity user =  userRepository.findById(idUser)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );
        return accountRepository.findByUser(user)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Account not found. Please check the user ID or username and try again."))
                );
    }
}
