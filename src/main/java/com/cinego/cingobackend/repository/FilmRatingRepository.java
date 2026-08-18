package com.cinego.cingobackend.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cinego.dto.FilmRating;
import com.cinego.dto.FilmRatingPk;

@CrossOrigin("http://localhost:4200")
@Repository
@RepositoryRestResource(exported = false)
public interface FilmRatingRepository extends CrudRepository<FilmRating, FilmRatingPk>{
	 /**
     * Lookup all the FilmRatings for a tour.
     *
     * @param filmId is the tour Identifier
     * @return a List of any found FilmRatings
     */
    List<FilmRating> findByPkFilmId(Long filmId);

    /**
     * Lookup a TourRating by the TourId and Customer Id
     * @param filmId tour identifier
     * @param customerId customer identifier
     * @return Optional of found FilmRatings.
     */
    Optional<FilmRating> findByPkFilmIdAndPkCustomerId(Long tourId, Long customerId);
}