package com.cinego.cingobackend.controller.admin;

import com.cinego.cingobackend.model.Seance;
import com.cinego.cingobackend.service.FilmService;
import com.cinego.cingobackend.service.SalleService;
import com.cinego.cingobackend.service.SeanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/seances")
@RequiredArgsConstructor
public class AdminSeanceController {

    private final SeanceService seanceService;
    private final FilmService filmService;
    private final SalleService salleService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("currentPage", "seances");
        model.addAttribute("seances", seanceService.getListAll());
        return "admin/seances/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("currentPage", "seances");
        model.addAttribute("seance", new Seance());
        model.addAttribute("films", filmService.getListAll());
        model.addAttribute("salles", salleService.getListAll());
        return "admin/seances/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("currentPage", "seances");
        model.addAttribute("seance", seanceService.get(id));
        model.addAttribute("films", filmService.getListAll());
        model.addAttribute("salles", salleService.getListAll());
        return "admin/seances/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Seance seance) {
        seanceService.save(seance);
        return "redirect:/admin/seances";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        seanceService.delete(id);
        return "redirect:/admin/seances";
    }
}
