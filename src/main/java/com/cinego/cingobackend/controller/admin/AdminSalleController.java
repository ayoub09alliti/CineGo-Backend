package com.cinego.cingobackend.controller.admin;

import com.cinego.cingobackend.model.Salle;
import com.cinego.cingobackend.service.SalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/salles")
@RequiredArgsConstructor
public class AdminSalleController {

    private final SalleService salleService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("currentPage", "salles");
        model.addAttribute("salles", salleService.getListAll());
        return "admin/salles/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("currentPage", "salles");
        model.addAttribute("salle", new Salle());
        return "admin/salles/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("currentPage", "salles");
        model.addAttribute("salle", salleService.get(id));
        return "admin/salles/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Salle salle) {
        salleService.save(salle);
        return "redirect:/admin/salles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        salleService.delete(id);
        return "redirect:/admin/salles";
    }
}
