package com.fizalise.taskmngr.repository;

import com.fizalise.taskmngr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    int PAGE_SIZE = 2;
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
