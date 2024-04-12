package com.example.usermanagementapi.facades;

import com.example.usermanagementapi.mappers.UserMapper;
import com.example.usermanagementapi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserFacade {
    private final Logger log = LoggerFactory.getLogger(UserFacade.class);
    private final UserMapper userMapper;
    private final UserService userService;

    public UserFacade(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }
}
