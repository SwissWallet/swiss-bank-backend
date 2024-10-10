package com.swiss.bank.service;

import com.swiss.bank.entity.User;
import com.swiss.bank.web.dto.UserCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserService userService;

    public UserService(UserService userService) {
        this.userService = userService;
    }
}
