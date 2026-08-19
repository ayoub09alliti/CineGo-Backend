package com.cinego.cingobackend.repository;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cinego.dto.InlinePersonne;
import com.cinego.cingobackend.model.Personne;
import com.cinego.cingobackend.model.Personne.TypePersonne;

@CrossOrigin("http://localhost:4200")
@Repository
@RepositoryRestResource(excerptProjection = InlinePersonne.class)
public interface PersonneRepository extends JpaRepository<Personne, Long> {
	Page<Personne> findByTypePersonne(TypePersonne typePersonne, Pageable pageable);
	Page<Personne> findByDateNaissanceGreaterThanEqual(Date dateNs, Pageable pageable);
	Page<Personne> findByNomContainingOrPrenomContaining(String nom, String prenom, Pageable pageable);
}