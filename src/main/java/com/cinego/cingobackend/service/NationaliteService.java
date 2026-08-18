package com.cinego.cingobackend.service;


import com.cinego.cingobackend.model.Nationalite;
import com.cinego.cingobackend.repository.NationaliteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class NationaliteService extends AbstractService<Nationalite, Long> {

    @Autowired
    private NationaliteRepository nationaliteRepository;
    

    @Override
    protected JpaRepository<Nationalite, Long> getRepository() {
        return nationaliteRepository;
    }

}