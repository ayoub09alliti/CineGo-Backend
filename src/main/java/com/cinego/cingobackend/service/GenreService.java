package com.cinego.cingobackend.service;

import com.cinego.cingobackend.model.Genre;
import com.cinego.cingobackend.repository.GenreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class GenreService extends AbstractService<Genre, Long> {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    protected JpaRepository<Genre, Long> getRepository() {
        return genreRepository;
    }

}