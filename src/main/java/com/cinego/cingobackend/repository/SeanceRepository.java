package com.cinego.cingobackend.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cinego.cingobackend.model.Seance;

@CrossOrigin("http://localhost:4200")
@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {
	List<Seance> findByDateProjection(Date dateProjection);
}