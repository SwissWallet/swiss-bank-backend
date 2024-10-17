package com.swiss.bank.web.controller;

import com.swiss.bank.service.CodePixService;
import jakarta.persistence.Entity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/codes")
public class CodePixController {

    private final CodePixService codePixService;

    public CodePixController(CodePixService codePixService) {
        this.codePixService = codePixService;
    }
}
