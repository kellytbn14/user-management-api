package com.example.usermanagementapi.repositories;

import com.example.usermanagementapi.dtos.FilterUserRequest;
import com.example.usermanagementapi.dtos.FilterUserResponse;
import com.example.usermanagementapi.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT new com.example.usermanagementapi.dtos.FilterUserResponse(" +
            "u.id, u.name, u.email, u.password, u.isActive, u.createdDate, u.lastUpdate, u.lastLogin) " +
            "FROM User u " +
            "WHERE (:#{#filter.name} IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', CAST(:#{#filter.name} AS string),'%'))) " +
            "AND (:#{#filter.email} IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', CAST(:#{#filter.email} AS string),'%'))) " +
            "AND (:#{#filter.isActive} IS NULL OR u.isActive = :#{#filter.isActive})"
    )
    Page<FilterUserResponse> filterUsersPage(@Param("filter") FilterUserRequest filter, Pageable pageable);
}
