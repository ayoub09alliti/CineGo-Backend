package com.cinego.cingobackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cinego.cingobackend.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
}