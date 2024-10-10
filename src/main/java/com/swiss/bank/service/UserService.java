package com.swiss.bank.service;

import com.swiss.bank.entity.UserEntity;
import com.swiss.bank.exception.NewPasswordInvalidException;
import com.swiss.bank.exception.ObjectNotFoundException;
import com.swiss.bank.exception.PasswordInvalidException;
import com.swiss.bank.exception.UserUniqueViolationException;
import com.swiss.bank.repository.IUserRepository;
import com.swiss.bank.web.dto.UserCreateDto;
import com.swiss.bank.web.dto.UserPasswordChangeDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserEntity saveUser(UserCreateDto dto){
        try{
            UserEntity userEntity = new UserEntity();
            userEntity.setName(dto.name());
            userEntity.setUsername(dto.username());
            userEntity.setCpf(dto.cpf());
            userEntity.setPhone(dto.phone());
            userEntity.setPassword(dto.password());
            return userRepository.save(userEntity);
        }catch (DataIntegrityViolationException ex){
            throw new UserUniqueViolationException(String.format("A user with this username= %s already exists. Please use a different username.", dto.username()));
        }
    }

    @Transactional(readOnly = true)
    public UserEntity findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );
    }

    @Transactional
    public void deleteUser(Long id){
        UserEntity userEntity = findById(id);
        userRepository.deleteById(id);
    }

    @Transactional
    public void changeUserPassword(UserPasswordChangeDto passwordChangeDto, Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );

        if (userEntity.getPassword() != passwordChangeDto.currentPassword()){
            throw new PasswordInvalidException("The current password provided is invalid. Please try again");
        }

        if(!passwordChangeDto.newPassword().equals(passwordChangeDto.confirmPassword())){
            throw new NewPasswordInvalidException("The new password provided is invalid. Please follow the password requirements.");
        }

        userEntity.setPassword(passwordChangeDto.newPassword());
        userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
