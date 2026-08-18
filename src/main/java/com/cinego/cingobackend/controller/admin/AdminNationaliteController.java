package com.cinego.cingobackend.controller.admin;

import com.cinego.cingobackend.model.Nationalite;
import com.cinego.cingobackend.service.NationaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/nationalites")
@RequiredArgsConstructor
public class AdminNationaliteController {

    private final NationaliteService nationaliteService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("nationalites", nationaliteService.getListAll());
        return "admin/nationalites/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("nationalite", new Nationalite());
        return "admin/nationalites/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("nationalite", nationaliteService.get(id));
        return "admin/nationalites/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Nationalite nationalite) {
        nationaliteService.save(nationalite);
        return "redirect:/admin/nationalites";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        nationaliteService.delete(id);
        return "redirect:/admin/nationalites";
    }
}