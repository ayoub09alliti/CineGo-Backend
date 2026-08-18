package com.cinego.cingobackend.controller.admin;

import com.cinego.cingobackend.model.Personne;
import com.cinego.cingobackend.service.NationaliteService;
import com.cinego.cingobackend.service.PersonneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/personnes")
@RequiredArgsConstructor
public class AdminPersonneController {

    private final PersonneService personneService;
    private final NationaliteService nationaliteService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("personnes", personneService.getListAll());
        return "admin/personnes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("personne", new Personne());
        model.addAttribute("nationalites", nationaliteService.getListAll());
        return "admin/personnes/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("personne", personneService.get(id));
        model.addAttribute("nationalites", nationaliteService.getListAll());
        return "admin/personnes/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Personne personne) {
        personneService.save(personne);
        return "redirect:/admin/personnes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        personneService.delete(id);
        return "redirect:/admin/personnes";
    }
}