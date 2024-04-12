package com.example.usermanagementapi.repositories;

import com.example.usermanagementapi.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {
    
}
