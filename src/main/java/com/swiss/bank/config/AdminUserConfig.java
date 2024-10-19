package com.swiss.bank.config;

import com.swiss.bank.entity.Account;
import com.swiss.bank.entity.Role;
import com.swiss.bank.entity.UserEntity;
import com.swiss.bank.repository.IAccountRepository;
import com.swiss.bank.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IAccountRepository accountRepository;

    public AdminUserConfig(IUserRepository userRepository, PasswordEncoder passwordEncoder, IAccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        var userAdmin = userRepository.findByUsername("admin@email.com");
        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("Admin already exists");
                },
                () -> {
                    var userEntity = new UserEntity();
                    userEntity.setUsername("admin@email.com");
                    userEntity.setPassword(passwordEncoder.encode("12345678"));
                    userEntity.setName("Admin");
                    userEntity.setRole(Role.ROLE_ADMIN);
                    userRepository.save(userEntity);

                    var account = new Account();
                    account.setUser(userEntity);
                    account.setAccountNumber("12345");
                    accountRepository.save(account);
                }
        );

    }
}
