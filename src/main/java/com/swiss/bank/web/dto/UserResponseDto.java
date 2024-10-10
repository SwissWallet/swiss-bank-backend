package com.swiss.bank.web.dto;

import com.swiss.bank.entity.Role;
import com.swiss.bank.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public record UserResponseDto(Long id,
                              String username,
                              String name,
                              String cpf,
                              String phone,
                              Role role) {

    public static UserResponseDto toUserResponse(UserEntity userEntity){
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getName(),
                userEntity.getCpf(),
                userEntity.getPhone(),
                userEntity.getRole()
        );
    }

    public static List<UserResponseDto> toListUserResponse(List<UserEntity> userEntities){
        return userEntities.stream()
                .map(userEntity -> toUserResponse(userEntity)).collect(Collectors.toList());
    }

}
