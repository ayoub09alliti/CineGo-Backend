package com.cinego.dto;

import com.cinego.cingobackend.model.Nationalite;
import com.cinego.cingobackend.model.Personne;
import com.cinego.cingobackend.model.Personne.TypePersonne;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlinePersonne", types = { Personne.class })
public interface InlinePersonne {

    Long getId();

    String getNom();

    String getPrenom();

    TypePersonne getTypePersonne();

    Nationalite getNationalite();
}