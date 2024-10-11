package com.swiss.bank.web.controller;

import com.swiss.bank.entity.Account;
import com.swiss.bank.jwt.JwtUserDetails;
import com.swiss.bank.service.AccountService;
import com.swiss.bank.web.dto.AccountResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<AccountResponseDto> getAccountByUser(@AuthenticationPrincipal JwtUserDetails userDetails){
        Account account = accountService.findByUser(userDetails.getId());
        return ResponseEntity.ok().body(AccountResponseDto.toResponse(account));
    }

}
