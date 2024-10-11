package com.swiss.bank.service;

import com.swiss.bank.repository.IAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
