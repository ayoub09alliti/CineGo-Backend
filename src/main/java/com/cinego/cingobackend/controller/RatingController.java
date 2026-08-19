package com.cinego.cingobackend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinego.cingobackend.model.Customers;
import com.cinego.cingobackend.repository.CustomersRepository;
import com.cinego.cingobackend.repository.FilmRatingRepository;
import com.cinego.dto.FilmRating;
import com.cinego.dto.FilmRatingPk;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping({"/api/ratings", "/api/custom/ratings"})
@RequiredArgsConstructor
public class RatingController {

    private final FilmRatingRepository filmRatingRepository;
    private final CustomersRepository customersRepository;

    @Data
    public static class RatingPayload {
        private Long filmId;
        private Integer score;
        private String comment;
    }

    @GetMapping("/film/{filmId}/average")
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable("filmId") Long filmId) {
        List<FilmRating> ratings = filmRatingRepository.findByPkFilmId(filmId);
        Map<String, Object> res = new LinkedHashMap<>();

        if (ratings == null || ratings.isEmpty()) {
            res.put("average", 4.8);
            res.put("count", 0);
            return ResponseEntity.ok(res);
        }

        double sum = 0.0;
        for (FilmRating r : ratings) {
            sum += (r.getScore() != null ? r.getScore() : 5);
        }
        double avg = Math.round((sum / ratings.size()) * 10.0) / 10.0;

        res.put("average", avg);
        res.put("count", ratings.size());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/film/{filmId}/all")
    public ResponseEntity<List<Map<String, Object>>> getFilmRatings(@PathVariable("filmId") Long filmId) {
        List<FilmRating> ratings = filmRatingRepository.findByPkFilmId(filmId);
        List<Map<String, Object>> result = new ArrayList<>();

        if (ratings != null) {
            for (FilmRating r : ratings) {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("id", r.getPk() != null ? r.getPk().getCustomerId() : 1);
                item.put("filmId", filmId);
                item.put("score", r.getScore());
                item.put("comment", r.getComment());
                item.put("createdAt", r.getAddedDate() != null ? r.getAddedDate().toString().split(" ")[0] : "2026-08-19");

                String customerName = "Client CineGo";
                if (r.getPk() != null && r.getPk().getCustomerId() != null) {
                    customerName = customersRepository.findById(r.getPk().getCustomerId())
                            .map(c -> c.getFirstname() + (c.getLastname() != null ? " " + c.getLastname().charAt(0) + "." : ""))
                            .orElse("Client #" + r.getPk().getCustomerId());
                }
                item.put("username", customerName);
                result.add(item);
            }
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/film/{filmId}/my-rating")
    public ResponseEntity<?> getMyRating(@PathVariable("filmId") Long filmId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.ok(null);
        }

        String identifier = authentication.getName();
        Customers customer = customersRepository.findByEmail(identifier)
                .or(() -> customersRepository.findByFirstname(identifier))
                .orElse(null);

        if (customer == null || customer.getId() == null) {
            return ResponseEntity.ok(null);
        }

        return filmRatingRepository.findByPkFilmIdAndPkCustomerId(filmId, customer.getId())
                .map(r -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("id", customer.getId());
                    item.put("filmId", filmId);
                    item.put("score", r.getScore());
                    item.put("comment", r.getComment());
                    item.put("username", customer.getFirstname());
                    item.put("createdAt", r.getAddedDate() != null ? r.getAddedDate().toString().split(" ")[0] : "2026-08-19");
                    return ResponseEntity.ok(item);
                })
                .orElse(ResponseEntity.ok(null));
    }

    @PostMapping
    public ResponseEntity<?> rateFilm(@RequestBody RatingPayload payload, Authentication authentication) {
        if (payload.getFilmId() == null || payload.getScore() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "filmId et score sont obligatoires."));
        }

        Long customerId = 1L;
        String customerName = "Client";

        if (authentication != null && authentication.isAuthenticated()) {
            String identifier = authentication.getName();
            Customers customer = customersRepository.findByEmail(identifier)
                    .or(() -> customersRepository.findByFirstname(identifier))
                    .orElse(null);
            if (customer != null && customer.getId() != null) {
                customerId = customer.getId();
                customerName = customer.getFirstname();
            }
        } else {
            // Find default first customer if non-authenticated client
            List<Customers> all = customersRepository.findAll();
            if (!all.isEmpty()) {
                customerId = all.get(0).getId();
                customerName = all.get(0).getFirstname();
            }
        }

        FilmRatingPk pk = new FilmRatingPk(payload.getFilmId(), customerId);
        FilmRating rating = new FilmRating(pk, payload.getScore());
        rating.setComment(payload.getComment());
        rating.setAddedDate(new Date());

        FilmRating saved = filmRatingRepository.save(rating);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", customerId);
        response.put("filmId", payload.getFilmId());
        response.put("score", saved.getScore());
        response.put("comment", saved.getComment());
        response.put("username", customerName);
        response.put("createdAt", new Date().toString().split(" ")[0]);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
