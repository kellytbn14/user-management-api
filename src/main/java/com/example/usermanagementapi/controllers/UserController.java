package com.example.usermanagementapi.controllers;

import com.example.usermanagementapi.dtos.ChangeActiveStatusUserRequest;
import com.example.usermanagementapi.dtos.CreateUserRequest;
import com.example.usermanagementapi.dtos.CreateUserResponse;
import com.example.usermanagementapi.dtos.UserDto;
import com.example.usermanagementapi.facades.UserFacade;
import com.example.usermanagementapi.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserFacade facade;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/register")
    @Operation(summary = "Register user with phones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<CreateUserResponse>> createUser(@RequestBody CreateUserRequest request) {
        var user = facade.createUser(request);
        return ResponseEntity.ok(new StandardResponse<>(user));
    }

    @PutMapping("/change-active-status")
    @Operation(summary = "Change the active status for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data updated successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<UserDto>> changeActiveStatus(@RequestBody ChangeActiveStatusUserRequest request) {
        var user = facade.changeActiveStatus(request);
        return ResponseEntity.ok(new StandardResponse<>(user));
    }
}