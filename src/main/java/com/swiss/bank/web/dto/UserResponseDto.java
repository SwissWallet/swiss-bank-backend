package com.swiss.bank.web.dto;

import com.swiss.bank.entity.Role;
import com.swiss.bank.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserResponseDto(Long id,
                              String username,
                              String name,
                              String cpf,
                              String phone,
                              Role role) {

    public static UserResponseDto toUserResponse(User user){
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getCpf(),
                user.getPhone(),
                user.getRole()
        );
    }

    public static List<UserResponseDto> toListUserResponse(List<User> users){
        return users.stream()
                .map(user -> toUserResponse(user)).collect(Collectors.toList());
    }

}
