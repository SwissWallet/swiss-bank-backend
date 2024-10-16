package com.swiss.bank.repository;

import com.swiss.bank.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseRepository extends JpaRepository<Purchase, Long> {
}
