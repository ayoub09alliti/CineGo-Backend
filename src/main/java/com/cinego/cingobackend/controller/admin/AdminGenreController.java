package com.cinego.cingobackend.controller.admin;

import com.cinego.cingobackend.model.Genre;
import com.cinego.cingobackend.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/genres")
@RequiredArgsConstructor
public class AdminGenreController {

    private final GenreService genreService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("currentPage", "genres");
        model.addAttribute("genres", genreService.getListAll());
        return "admin/genres/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("currentPage", "genres");
        model.addAttribute("genre", new Genre());
        return "admin/genres/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("currentPage", "genres");
        model.addAttribute("genre", genreService.get(id));
        return "admin/genres/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Genre genre) {
        genreService.save(genre);
        return "redirect:/admin/genres";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        genreService.delete(id);
        return "redirect:/admin/genres";
    }
}
