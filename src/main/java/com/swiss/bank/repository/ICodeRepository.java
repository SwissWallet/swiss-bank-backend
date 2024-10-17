package com.swiss.bank.repository;

import com.swiss.bank.entity.CodePix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICodeRepository extends JpaRepository<CodePix, Long> {
}
