package com.example.usermanagementapi.services;

import com.example.usermanagementapi.entities.Phone;
import com.example.usermanagementapi.repositories.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PhoneServiceTest {

    private PhoneService phoneService;

    private PhoneRepository phoneRepositoryMock;

    @BeforeEach
    public void setUp() {
        phoneRepositoryMock = mock(PhoneRepository.class);
        phoneService = new PhoneService(phoneRepositoryMock);
    }

    @Test
    public void shouldSaveAllPhones() {
        // Arrange
        List<Phone> phonesToSave = List.of(new Phone(), new Phone());

        List<Phone> savedPhones = List.of(new Phone(), new Phone());
        when(phoneRepositoryMock.saveAll(phonesToSave)).thenReturn(savedPhones);

        // Act
        List<Phone> result = phoneService.saveAll(phonesToSave);

        // Assert
        assertNotNull(result);
        assertEquals(savedPhones.size(), result.size());
        assertTrue(result.containsAll(savedPhones));
        verify(phoneRepositoryMock, atLeastOnce()).saveAll(phonesToSave);
    }

    @Test
    public void shouldSearchPhones() {
        // Arrange
        UUID userId = UUID.randomUUID();

        List<Phone> phones = List.of(new Phone(), new Phone());
        when(phoneRepositoryMock.findAllByUserId(userId)).thenReturn(phones);

        // Act
        List<Phone> result = phoneService.findAllByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(phones.size(), result.size());
        assertTrue(result.containsAll(phones));
        verify(phoneRepositoryMock, atLeastOnce()).findAllByUserId(userId);
    }
}
