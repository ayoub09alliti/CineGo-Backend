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
    public String save(@ModelAttribute Film film) {
        filmService.save(film);
        return "redirect:/admin/films";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        filmService.delete(id);
        return "redirect:/admin/films";
    }
}
