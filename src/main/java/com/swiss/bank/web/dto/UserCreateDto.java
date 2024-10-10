package com.swiss.bank.web.dto;

public record UserCreateDto(String username,
                            String name,
                            String cpf,
                            String phone,
                            String password) {
}
