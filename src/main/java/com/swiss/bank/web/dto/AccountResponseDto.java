package com.swiss.bank.web.dto;

import com.swiss.bank.entity.Account;
import com.swiss.bank.entity.Card;
import com.swiss.bank.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public record AccountResponseDto(Long id,
                                 String accountNumber,
                                 float balance,
                                 UserResponseDto user) {

    public static AccountResponseDto toResponse(Account account){
        return new AccountResponseDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                UserResponseDto.toUserResponse(account.getUser())
        );
    }

    public static List<AccountResponseDto> toListResponse(List<Account> accounts){
        return accounts.stream()
                .map(account -> toResponse(account)).collect(Collectors.toList());
    }
}
