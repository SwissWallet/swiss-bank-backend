package com.swiss.bank.repository;

import com.swiss.bank.entity.Account;
import com.swiss.bank.entity.Extract;
import com.swiss.bank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IExtractRepository extends JpaRepository<Extract, Long> {
    Extract findExtractByAccount(Account account);

    List<Extract> findAllByAccount(Account account);

    void deleteAllByAccount(Account account);

}
