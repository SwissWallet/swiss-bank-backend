package com.swiss.bank.service;

import com.swiss.bank.entity.User;
import com.swiss.bank.exception.ObjectNotFoundException;
import com.swiss.bank.exception.UserUniqueViolationException;
import com.swiss.bank.repository.IUserRepository;
import com.swiss.bank.web.dto.UserCreateDto;
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
    public User saveUser(UserCreateDto dto){
        try{
            User user = new User();
            user.setName(dto.name());
            user.setUsername(dto.username());
            user.setCpf(dto.cpf());
            user.setPhone(dto.phone());
            user.setPassword(dto.password());
            return userRepository.save(user);
        }catch (DataIntegrityViolationException ex){
            throw new UserUniqueViolationException(String.format("A user with this username= %s already exists. Please use a different username.", dto.username()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("User not found. Please check the user ID or username and try again."))
                );
    }

    @Transactional
    public void deleteUser(Long id){
        User user = findById(id);
        userRepository.deleteById(id);
    }

}
