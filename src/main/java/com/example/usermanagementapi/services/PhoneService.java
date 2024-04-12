package com.example.usermanagementapi.services;

import com.example.usermanagementapi.entities.Phone;
import com.example.usermanagementapi.repositories.PhoneRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PhoneService {
    private final PhoneRepository repository;

    public PhoneService(PhoneRepository repository) {
        this.repository = repository;
    }

    public List<Phone> saveAll(List<Phone> phones) {
        return repository.saveAll(phones);
    }

    public List<Phone> findAllByUserId(UUID id) {
        return repository.findAllByUserId(id);
    }
}