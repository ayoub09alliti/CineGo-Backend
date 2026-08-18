package com.cinego.dto;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FilmRatingPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "customer_id")
    private Long customerId;

    public FilmRatingPk() {
    }

    public FilmRatingPk(Long filmId, Long customerId) {
        this.filmId = filmId;
        this.customerId = customerId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmRatingPk that = (FilmRatingPk) o;
        return Objects.equals(filmId, that.filmId) && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, customerId);
    }
}