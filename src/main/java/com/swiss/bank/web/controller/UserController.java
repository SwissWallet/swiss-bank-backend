package com.swiss.bank.web.controller;

import com.swiss.bank.entity.User;
import com.swiss.bank.service.UserService;
import com.swiss.bank.web.dto.UserCreateDto;
import com.swiss.bank.web.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid UserCreateDto dto){
        User user = userService.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDto.toUserResponse(user));
    }
}
