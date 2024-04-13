package com.example.usermanagementapi.services;

import com.example.usermanagementapi.dtos.FilterUserRequest;
import com.example.usermanagementapi.dtos.FilterUserResponse;
import com.example.usermanagementapi.entities.User;
import com.example.usermanagementapi.handlers.exceptions.DataDuplicateException;
import com.example.usermanagementapi.handlers.exceptions.DataNotFoundException;
import com.example.usermanagementapi.handlers.exceptions.RequiredException;
import com.example.usermanagementapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceTest {

    private UserService userService;

    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        userService = new UserService(userRepositoryMock);
    }

    @Test
    public void shouldSearchUserByEmail() {

        //Arrange
        String email = "test@email.com";

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Test")
                .email(email)
                .password("1234")
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));

        //Act
        User result = userService.findByEmail(email);

        //Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void shouldThrowExceptionWhenEmailDoesNotExist() {
        // Arrange
        String email = "test@email.com";
        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> {
            userService.findByEmail(email);
        });
    }

    @Test
    public void shouldSearchUserById() {

        //Arrange
        UUID id = UUID.randomUUID();

        User user = User.builder()
                .id(id)
                .name("Test")
                .email("test@email.com")
                .password("1234")
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        when(userRepositoryMock.findById(id)).thenReturn(Optional.of(user));

        //Act
        User result = userService.findById(id);

        //Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExist() {

        //Arrange
        UUID id = UUID.randomUUID();

        when(userRepositoryMock.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(DataNotFoundException.class, () -> userService.findById(id));
    }

    @Test
    public void shouldThrowExceptionWhenEmailExists() {

        //Arrange
        UUID id = UUID.randomUUID();
        String email = "test@email.com";
        User user = User.builder()
                .id(id)
                .name("Test")
                .email(email)
                .password("1234")
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));

        //Act & Assert
        assertThrows(DataDuplicateException.class, () -> userService.validateEmailExists(email));
    }

    @Test
    public void shouldNotThrowExceptionWhenEmailDoesNotExists() {

        //Arrange
        String email = "test@email.com";
        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.empty());

        //Act & Assert
        assertDoesNotThrow(() -> userService.validateEmailExists(email));
    }

    @Test
    public void shouldNotThrowExceptionWhenUserExists() {

        //Arrange
        UUID id = UUID.randomUUID();
        String email = "test@email.com";
        User user = User.builder()
                .id(id)
                .name("Test")
                .email(email)
                .password("1234")
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        when(userRepositoryMock.findById(id)).thenReturn(Optional.of(user));

        //Act & Assert
        assertDoesNotThrow(() -> userService.validateUser(id));
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExists() {

        //Arrange
        UUID id = UUID.randomUUID();
        when(userRepositoryMock.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(DataNotFoundException.class, () -> userService.validateUser(id));
    }

    @Test
    public void shouldSaveUser() {

        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@email.com";
        User userToSave = User.builder()
                .id(id)
                .name("Test")
                .email(email)
                .password("1234")
                .isActive(true)
                .build();

        User savedUser = User.builder()
                .id(id)
                .name("Test")
                .email(email)
                .password("1234")
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        when(userRepositoryMock.save(userToSave)).thenReturn(savedUser);

        // Act
        User result = userService.save(userToSave);

        // Assert
        assertSame(savedUser, result);
        verify(userRepositoryMock, atLeastOnce()).save(userToSave);
    }

    @Test
    public void shouldThrowExceptionWhenUpdateUserWhenUserIdNotProvided() {

        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@email.com";
        User user = User.builder()
                .id(null)
                .name("Test")
                .email(email)
                .password("1234")
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        // Act & Assert
        assertThrows(RequiredException.class, () -> userService.update(user));
        verify(userRepositoryMock, never()).save(user);
    }

    @Test
    public void shouldThrowExceptionWhenUpdateUserWhenUserIdDoNotExists() {

        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@email.com";
        User user = User.builder()
                .id(id)
                .name("Test")
                .email(email)
                .password("1234")
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        when(userRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> userService.update(user));
        verify(userRepositoryMock, never()).save(user);
    }

    @Test
    public void shouldUpdateUser() {

        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@email.com";
        User user = User.builder()
                .id(id)
                .name("Test")
                .email(email)
                .password("1234")
                .isActive(true)
                .createdDate(LocalDateTime.now().minusDays(1))
                .lastUpdate(LocalDateTime.now().minusDays(1))
                .lastLogin(LocalDateTime.now().minusDays(1))
                .build();

        User userUpdated = User.builder()
                .id(id)
                .name("Test")
                .email(email)
                .password("1234")
                .isActive(true)
                .createdDate(LocalDateTime.now().minusDays(1))
                .lastUpdate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now().minusDays(1))
                .build();

        when(userRepositoryMock.findById(id)).thenReturn(Optional.of(user));
        when(userRepositoryMock.save(user)).thenReturn(userUpdated);

        // Act
        User result = userService.update(user);

        // Assert
        assertSame(userUpdated, result);
        verify(userRepositoryMock, atLeastOnce()).save(user);
    }

    @Test
    public void shouldFilterUsersPage() {
        // Arrange
        FilterUserRequest filter = new FilterUserRequest();
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        Page<FilterUserResponse> expectedPage = new PageImpl<>(List.of(new FilterUserResponse()));
        when(userRepositoryMock.filterUsersPage(filter, pageable)).thenReturn(expectedPage);

        // Act
        Page<FilterUserResponse> result = userService.filterUsersPage(filter, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPage, result);
    }
}
