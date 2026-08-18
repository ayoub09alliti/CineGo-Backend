package com.cinego.cingobackend.controller.admin;

import com.cinego.cingobackend.model.Media;
import com.cinego.cingobackend.service.FilmService;
import com.cinego.cingobackend.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/medias")
@RequiredArgsConstructor
public class AdminMediaController {

    private final MediaService mediaService;
    private final FilmService filmService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("medias", mediaService.getListAll());
        return "admin/medias/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("media", new Media());
        model.addAttribute("films", filmService.getListAll());
        return "admin/medias/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("media", mediaService.get(id));
        model.addAttribute("films", filmService.getListAll());
        return "admin/medias/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Media media) {
        mediaService.save(media);
        return "redirect:/admin/medias";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        mediaService.delete(id);
        return "redirect:/admin/medias";
    }
}