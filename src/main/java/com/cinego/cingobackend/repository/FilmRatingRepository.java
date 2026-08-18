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
     * Recherche toutes les notes d'un film.
     *
     * @param filmId est l'identifiant du film
     * @return une liste des notes trouvées
     */
    List<FilmRating> findByPkFilmId(Long filmId);

    /**
     * Recherche la note d'un film par client.
     *
     * @param filmId identifiant du film
     * @param customerId identifiant du client
     * @return Optional de la note trouvée
     */
    Optional<FilmRating> findByPkFilmIdAndPkCustomerId(Long filmId, Long customerId);
}