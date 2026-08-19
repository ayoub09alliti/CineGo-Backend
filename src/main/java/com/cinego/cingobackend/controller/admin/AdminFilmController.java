package com.cinego.cingobackend.controller.admin;

import com.cinego.cingobackend.model.Film;
import com.cinego.cingobackend.service.FilmService;
import com.cinego.cingobackend.service.GenreService;
import com.cinego.cingobackend.service.NationaliteService;
import com.cinego.cingobackend.service.PersonneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin/films")
@RequiredArgsConstructor
public class AdminFilmController {

    private final FilmService filmService;
    private final GenreService genreService;
    private final NationaliteService nationaliteService;
    private final PersonneService personneService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("currentPage", "films");
        model.addAttribute("films", filmService.getListAll());
        return "admin/films/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("currentPage", "films");
        model.addAttribute("film", new Film());
        model.addAttribute("genres", genreService.getListAll());
        model.addAttribute("nationalites", nationaliteService.getListAll());
        model.addAttribute("personnes", personneService.getListAll());
        return "admin/films/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("currentPage", "films");
        model.addAttribute("film", filmService.get(id));
        model.addAttribute("genres", genreService.getListAll());
        model.addAttribute("nationalites", nationaliteService.getListAll());
        model.addAttribute("personnes", personneService.getListAll());
        return "admin/films/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Film film,
                       @RequestParam(value = "photoFile", required = false) MultipartFile photoFile) {
        if (photoFile != null && !photoFile.isEmpty()) {
            String oldPhoto = film.getPhoto();
            film.setPhoto(storePhoto(photoFile));
            if (oldPhoto != null && !oldPhoto.isEmpty()) {
                deleteStoredPhoto(oldPhoto);
            }
        }
        filmService.save(film);
        return "redirect:/admin/films";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        Film film = filmService.get(id);
        if (film.getPhoto() != null && !film.getPhoto().isEmpty()) {
            deleteStoredPhoto(film.getPhoto());
        }
        filmService.delete(id);
        return "redirect:/admin/films";
    }

    private String storePhoto(MultipartFile file) {
        try {
            Path dir = Paths.get("uploads", "films").toAbsolutePath().normalize();
            Files.createDirectories(dir);
            String ext = "";
            String original = file.getOriginalFilename();
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf("."));
            }
            String name = UUID.randomUUID().toString().replace("-", "") + ext;
            file.transferTo(dir.resolve(name).toFile());
            return name;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du stockage de la photo", e);
        }
    }

    private void deleteStoredPhoto(String name) {
        try {
            Path file = Paths.get("uploads", "films").toAbsolutePath().normalize().resolve(name);
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
        }
    }
}
