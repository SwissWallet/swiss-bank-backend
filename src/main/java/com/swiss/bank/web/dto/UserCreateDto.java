package com.swiss.bank.web.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record UserCreateDto(@NotBlank
                            String username,
                            @NotBlank
                            String name,
                            @CPF
                            String cpf,
                            @NotBlank
                            String phone,
                            @NotBlank
                            String password) {
}
