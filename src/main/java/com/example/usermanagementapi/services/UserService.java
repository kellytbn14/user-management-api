package com.example.usermanagementapi.services;

import com.example.usermanagementapi.dtos.FilterUserRequest;
import com.example.usermanagementapi.dtos.FilterUserResponse;
import com.example.usermanagementapi.entities.User;
import com.example.usermanagementapi.handlers.exceptions.DataDuplicateException;
import com.example.usermanagementapi.handlers.exceptions.DataNotFoundException;
import com.example.usermanagementapi.repositories.UserRepository;
import com.example.usermanagementapi.utils.CustomUtilService;
import com.example.usermanagementapi.utils.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

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

    public User update(User user) {
        CustomUtilService.ValidateRequired(user.getId());
        validateUser(user.getId());
        user.setLastUpdate(LocalDateTime.now());
        return repository.save(user);
    }

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.USER_NOT_FOUND_EXCEPTION));
    }

    public void validateEmailExists(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new DataDuplicateException(MessageResponse.USER_ALREADY_EXISTS);
        }
    }

    public void validateUser(UUID id) {
        repository.findById(id).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.USER_NOT_FOUND_EXCEPTION));
    }

    public Page<FilterUserResponse> filterUsersPage(FilterUserRequest filter, Pageable pageable) {
        return repository.filterUsersPage(filter, pageable);
    }
}