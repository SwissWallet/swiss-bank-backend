package com.swiss.bank.web.controller;

import com.swiss.bank.entity.UserEntity;
import com.swiss.bank.service.UserService;
import com.swiss.bank.web.dto.UserCreateDto;
import com.swiss.bank.web.dto.UserPasswordChangeDto;
import com.swiss.bank.web.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid UserCreateDto dto){
        UserEntity userEntity = userService.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDto.toUserResponse(userEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        UserEntity userEntity = userService.findById(id);
        return ResponseEntity.ok().body(UserResponseDto.toUserResponse(userEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<Void> updateUserPassword(@RequestBody @Valid UserPasswordChangeDto passwordChangeDto,
                                                   @PathVariable Long id){
        userService.changeUserPassword(passwordChangeDto, id);
        return ResponseEntity.ok().build();
    }
}
