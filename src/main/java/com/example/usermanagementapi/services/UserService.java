package com.example.usermanagementapi.services;

import com.example.usermanagementapi.entities.User;
import com.example.usermanagementapi.handlers.exceptions.DataDuplicateException;
import com.example.usermanagementapi.repositories.UserRepository;
import com.example.usermanagementapi.utils.MessageResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        user.setCreatedDate(LocalDateTime.now());
        user.setLastUpdate(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        return repository.save(user);
    }

    public void validateEmailExists(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new DataDuplicateException(MessageResponse.USER_ALREADY_EXISTS);
        }
    }
}