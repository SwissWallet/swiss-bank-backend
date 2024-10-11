package com.swiss.bank.repository;

import com.swiss.bank.entity.Account;
import com.swiss.bank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByUser(UserEntity user);

    Optional<Account> findByUser(UserEntity user);
}
