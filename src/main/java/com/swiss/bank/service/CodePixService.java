package com.swiss.bank.service;

import com.swiss.bank.repository.ICodeRepository;
import org.springframework.stereotype.Service;

@Service
public class CodePixService {

    private final ICodeRepository codeRepository;

    public CodePixService(ICodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }
}
