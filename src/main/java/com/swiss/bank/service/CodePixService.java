package com.swiss.bank.service;

import com.swiss.bank.entity.CodePix;
import com.swiss.bank.repository.ICodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class CodePixService {

    private final ICodeRepository codeRepository;

    public CodePixService(ICodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Transactional
    public String createCodePix(float value){
        CodePix codePix = new CodePix();
        codePix.setValue(value);
        codePix.setCode(generateUniqueCode());
        codePix = codeRepository.save(codePix);
        return codePix.getCode();
    }

    public String generateUniqueCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder uniqueString = new StringBuilder(30);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        boolean exists;

        do {
            uniqueString.setLength(0);
            for (int i = 0; i < 30; i++) {
                int index = random.nextInt(characters.length());
                uniqueString.append(characters.charAt(index));
            }

            Optional<CodePix> codeExists = codeRepository.findCodePixByCode(uniqueString.toString());
            exists = codeExists.isPresent();
        } while (exists);

        return uniqueString.toString();
    }
}
