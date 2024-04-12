package com.example.usermanagementapi.repositories;

import com.example.usermanagementapi.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {

    @Query(value = "SELECT p " +
            "FROM Phone p " +
            "WHERE p.userId = :userId "
    )
    List<Phone> findAllByUserId(@Param("userId") UUID userId);
}
