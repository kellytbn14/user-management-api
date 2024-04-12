package com.example.usermanagementapi.controllers;

import com.example.usermanagementapi.facades.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserFacade facade;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserFacade facade) {
        this.facade = facade;
    }

}