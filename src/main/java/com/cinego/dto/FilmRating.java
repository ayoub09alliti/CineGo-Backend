package com.cinego.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "film_rating")
public class FilmRating {

    @EmbeddedId
    private FilmRatingPk pk;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer score;

    @Column(length = 200)
    private String comment;

    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;

    public FilmRating() {
    }

    public FilmRating(FilmRatingPk pk, Integer score) {
        this.pk = pk;
        this.score = score;
    }

    public FilmRatingPk getPk() {
        return pk;
    }

    public void setPk(FilmRatingPk pk) {
        this.pk = pk;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}