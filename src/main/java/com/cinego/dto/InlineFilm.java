package com.cinego.dto;

import com.cinego.cingobackend.model.Film;
import com.cinego.cingobackend.model.Genre;
import com.cinego.cingobackend.model.Nationalite;
import com.cinego.cingobackend.model.Personne;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineFilm", types = { Film.class })
public interface InlineFilm {

    Long getId();

    String getTitre();

    int getDuree();

    int getAnnee();

    String getPhoto();

    Genre getGenre();

    Nationalite getNationalite();

    Personne getRealisateur();
}