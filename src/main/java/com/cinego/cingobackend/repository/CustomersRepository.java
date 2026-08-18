package com.cinego.cingobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cinego.cingobackend.model.Customers;

@CrossOrigin("http://localhost:4200")
@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {

}