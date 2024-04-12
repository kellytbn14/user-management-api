package com.example.usermanagementapi.controllers;

import com.example.usermanagementapi.dtos.*;
import com.example.usermanagementapi.facades.UserFacade;
import com.example.usermanagementapi.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PostMapping("/filter")
    @Operation(summary = "Search users with filters and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data found successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "Page you want to retrieve (0..N)", name = "page",
                    content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
            @Parameter(in = ParameterIn.QUERY, description = "Number of records per page.", name = "size",
                    content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
            @Parameter(in = ParameterIn.QUERY, description = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.", name = "sort",
                    content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))))
    })
    public ResponseEntity<StandardResponse<Page<FilterUserResponse>>> filterUsersPage(
            @RequestBody FilterUserRequest filter, Pageable pageable) {
        var users = facade.filterUsersPage(filter, pageable);
        return ResponseEntity.ok(new StandardResponse<>(users));
    }

    @GetMapping("/user-phones/{userId}")
    @Operation(summary = "Search user with phones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data searched successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<UserDto>> getUserWithPhones(@PathVariable UUID userId) {
        var result = facade.getUserWithPhones(userId);
        return ResponseEntity.ok(new StandardResponse<>(result));
    }
}