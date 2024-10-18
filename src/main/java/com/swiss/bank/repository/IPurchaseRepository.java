package com.swiss.bank.repository;

import com.swiss.bank.entity.Purchase;
import com.swiss.bank.entity.StatusPurhcase;
import com.swiss.bank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByUser(UserEntity user);

    Purchase findByCodePix(String codePix);

    void deleteByUser(UserEntity user);

    List<Purchase> findAllByStatus(StatusPurhcase statusPurhcase);
}
