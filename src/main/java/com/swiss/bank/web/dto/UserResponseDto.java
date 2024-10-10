package com.swiss.bank.web.dto;

public record UserResponseDto(Long id,
                              String username,
                              String name,
                              String cpf,
                              String phone) {
}
