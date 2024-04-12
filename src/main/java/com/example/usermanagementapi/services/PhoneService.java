package com.example.usermanagementapi.services;

import com.example.usermanagementapi.repositories.PhoneRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PhoneService {
    private final PhoneRepository repository;

    public PhoneService(PhoneRepository repository) {
        this.repository = repository;
    }
}