package com.cinego.cingobackend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinego.cingobackend.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}