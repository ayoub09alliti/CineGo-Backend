package com.cinego.cingobackend.config;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cinego.cingobackend.entity.User;
import com.cinego.cingobackend.model.Customers;
import com.cinego.cingobackend.model.Film;
import com.cinego.cingobackend.model.Genre;
import com.cinego.cingobackend.model.Media;
import com.cinego.cingobackend.model.Media.TypeMedia;
import com.cinego.cingobackend.model.Nationalite;
import com.cinego.cingobackend.model.Personne;
import com.cinego.cingobackend.model.Personne.TypePersonne;
import com.cinego.cingobackend.model.Salle;
import com.cinego.cingobackend.model.Seance;
import com.cinego.cingobackend.repository.CustomersRepository;
import com.cinego.cingobackend.repository.FilmRepository;
import com.cinego.cingobackend.repository.GenreRepository;
import com.cinego.cingobackend.repository.MediaRepository;
import com.cinego.cingobackend.repository.NationaliteRepository;
import com.cinego.cingobackend.repository.PersonneRepository;
import com.cinego.cingobackend.repository.SalleRepository;
import com.cinego.cingobackend.repository.SeanceRepository;
import com.cinego.cingobackend.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private NationaliteRepository nationaliteRepository;

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (databaseNotEmpty()) {
            System.out.println(">>> La base de donnees contient deja des donnees, seed ignore.");
            return;
        }

        Genre action = new Genre();
        action.setLibelle("Action");
        Genre comedie = new Genre();
        comedie.setLibelle("Comedie");
        Genre drame = new Genre();
        drame.setLibelle("Drame");
        Genre sf = new Genre();
        sf.setLibelle("Science-Fiction");
        genreRepository.saveAll(Arrays.asList(action, comedie, drame, sf));

        Nationalite fr = new Nationalite();
        fr.setLibelle("Francaise");
        Nationalite usa = new Nationalite();
        usa.setLibelle("Americaine");
        Nationalite sen = new Nationalite();
        sen.setLibelle("Senegalaise");
        nationaliteRepository.saveAll(Arrays.asList(fr, usa, sen));

        Personne tarantino = new Personne();
        tarantino.setNom("Tarantino");
        tarantino.setPrenom("Quentin");
        tarantino.setTypePersonne(TypePersonne.REALISATEUR);
        tarantino.setDateNaissance(date(1963, 3, 27));
        tarantino.setNationalite(usa);

        Personne dicaprio = new Personne();
        dicaprio.setNom("DiCaprio");
        dicaprio.setPrenom("Leonardo");
        dicaprio.setTypePersonne(TypePersonne.ACTEUR);
        dicaprio.setDateNaissance(date(1974, 11, 11));
        dicaprio.setNationalite(usa);

        Personne sembene = new Personne();
        sembene.setNom("Sembene");
        sembene.setPrenom("Ousmane");
        sembene.setTypePersonne(TypePersonne.REALISATEUR);
        sembene.setDateNaissance(date(1923, 1, 1));
        sembene.setNationalite(sen);

        Personne lynch = new Personne();
        lynch.setNom("Lynch");
        lynch.setPrenom("David");
        lynch.setTypePersonne(TypePersonne.REALISATEUR);
        lynch.setDateNaissance(date(1946, 1, 20));
        lynch.setNationalite(usa);

        personneRepository.saveAll(Arrays.asList(tarantino, dicaprio, sembene, lynch));

        Salle salle1 = new Salle();
        salle1.setNumero(1);
        salle1.setCapacite(120);
        Salle salle2 = new Salle();
        salle2.setNumero(2);
        salle2.setCapacite(80);
        salleRepository.saveAll(Arrays.asList(salle1, salle2));

        Film pulpFiction = new Film();
        pulpFiction.setTitre("Pulp Fiction");
        pulpFiction.setDuree(154);
        pulpFiction.setAnnee(1994);
        pulpFiction.setGenre(action);
        pulpFiction.setNationalite(usa);
        pulpFiction.setRealisateur(tarantino);
        pulpFiction.setActeurs(new ArrayList<>(Arrays.asList(dicaprio)));

        Film inception = new Film();
        inception.setTitre("Inception");
        inception.setDuree(148);
        inception.setAnnee(2010);
        inception.setGenre(sf);
        inception.setNationalite(usa);
        inception.setRealisateur(lynch);
        inception.setActeurs(new ArrayList<>(Arrays.asList(dicaprio)));

        Film mandabi = new Film();
        mandabi.setTitre("Mandabi");
        mandabi.setDuree(90);
        mandabi.setAnnee(1968);
        mandabi.setGenre(drame);
        mandabi.setNationalite(sen);
        mandabi.setRealisateur(sembene);
        mandabi.setActeurs(new ArrayList<>());

        Film killBill = new Film();
        killBill.setTitre("Kill Bill");
        killBill.setDuree(111);
        killBill.setAnnee(2003);
        killBill.setGenre(action);
        killBill.setNationalite(usa);
        killBill.setRealisateur(tarantino);
        killBill.setActeurs(new ArrayList<>(Arrays.asList(dicaprio)));

        filmRepository.saveAll(Arrays.asList(pulpFiction, inception, mandabi, killBill));

        Media posterPulp = new Media();
        posterPulp.setMedia("poster-pulp-fiction.jpg");
        posterPulp.setTypeMedia(TypeMedia.IMAGE);
        posterPulp.setFilm(pulpFiction);

        Media baInception = new Media();
        baInception.setMedia("ba-inception.mp4");
        baInception.setTypeMedia(TypeMedia.VIDEO);
        baInception.setFilm(inception);

        Media posterMandabi = new Media();
        posterMandabi.setMedia("poster-mandabi.jpg");
        posterMandabi.setTypeMedia(TypeMedia.IMAGE);
        posterMandabi.setFilm(mandabi);

        mediaRepository.saveAll(Arrays.asList(posterPulp, baInception, posterMandabi));

        Seance seance1 = new Seance();
        seance1.setDateProjection(date(2026, 8, 20));
        seance1.setHeureDebut(time(14, 0));
        seance1.setHeureFin(time(16, 30));
        seance1.setFilm(pulpFiction);
        seance1.setSalle(salle1);

        Seance seance2 = new Seance();
        seance2.setDateProjection(date(2026, 8, 20));
        seance2.setHeureDebut(time(18, 0));
        seance2.setHeureFin(time(20, 30));
        seance2.setFilm(inception);
        seance2.setSalle(salle1);

        Seance seance3 = new Seance();
        seance3.setDateProjection(date(2026, 8, 21));
        seance3.setHeureDebut(time(15, 0));
        seance3.setHeureFin(time(17, 0));
        seance3.setFilm(mandabi);
        seance3.setSalle(salle2);

        seanceRepository.saveAll(Arrays.asList(seance1, seance2, seance3));

        Customers awa = new Customers();
        awa.setFirstname("Awa");
        awa.setLastname("Diallo");
        awa.setEmail("awa.diallo@mail.com");

        Customers jean = new Customers();
        jean.setFirstname("Jean");
        jean.setLastname("Dupont");
        jean.setEmail("jean.dupont@mail.com");

        customersRepository.saveAll(Arrays.asList(awa, jean));

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@cinego.com");
        admin.setPassword(passwordEncoder.encode("1111"));

        User userAwa = new User();
        userAwa.setUsername("awa");
        userAwa.setEmail("awa@cinego.com");
        userAwa.setPassword(passwordEncoder.encode("awa123"));

        userRepository.saveAll(Arrays.asList(admin, userAwa));

        System.out.println(">>> Seed termine : "
                + genreRepository.count() + " genres, "
                + nationaliteRepository.count() + " nationalites, "
                + personneRepository.count() + " personnes, "
                + filmRepository.count() + " films, "
                + salleRepository.count() + " salles, "
                + seanceRepository.count() + " seances, "
                + mediaRepository.count() + " medias, "
                + customersRepository.count() + " clients, "
                + userRepository.count() + " utilisateurs.");
    }

    private boolean databaseNotEmpty() {
        return genreRepository.count() > 0
                || nationaliteRepository.count() > 0
                || personneRepository.count() > 0
                || filmRepository.count() > 0
                || salleRepository.count() > 0
                || seanceRepository.count() > 0
                || mediaRepository.count() > 0
                || customersRepository.count() > 0
                || userRepository.count() > 0;
    }

    private Date date(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, day);
        return cal.getTime();
    }

    private Date time(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(1970, Calendar.JANUARY, 1, hour, minute);
        return cal.getTime();
    }
}
