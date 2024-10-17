package com.swiss.bank.repository;

import com.swiss.bank.entity.CodePix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICodeRepository extends JpaRepository<CodePix, Long> {
    Optional<CodePix> findCodePixByCode(String string);
}
