package com.swiss.bank.web.controller;

import com.swiss.bank.jwt.JwtUserDetails;
import com.swiss.bank.service.CodePixService;
import io.jsonwebtoken.Jwt;
import jakarta.persistence.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/codes")
public class CodePixController {

    private final CodePixService codePixService;

    public CodePixController(CodePixService codePixService) {
        this.codePixService = codePixService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> saveCodePix(@RequestParam("value") float value){
        String codePix = codePixService.createCodePix(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(codePix);
    }

    @PostMapping("/payment")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> paymentCodePix(@RequestParam String code, @AuthenticationPrincipal JwtUserDetails userDetails){
        codePixService.paymentCodePix(code, userDetails.getId());
        return ResponseEntity.ok().build();
    }

}
