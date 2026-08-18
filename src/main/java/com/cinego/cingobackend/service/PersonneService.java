package com.cinego.cingobackend.service;


import com.cinego.cingobackend.model.Personne;
import com.cinego.cingobackend.repository.PersonneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonneService extends AbstractService<Personne, Long> {

    @Autowired
    private PersonneRepository personneRepository;

    @Override
    protected JpaRepository<Personne, Long> getRepository() {
        return personneRepository;
    }
 
}