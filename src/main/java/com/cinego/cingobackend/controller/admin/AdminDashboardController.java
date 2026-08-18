package com.cinego.cingobackend.controller.admin;

import com.cinego.cingobackend.service.FilmService;
import com.cinego.cingobackend.service.GenreService;
import com.cinego.cingobackend.service.SalleService;
import com.cinego.cingobackend.service.SeanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final FilmService filmService;
    private final GenreService genreService;
    private final SalleService salleService;
    private final SeanceService seanceService;

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("filmCount", filmService.count());
        model.addAttribute("genreCount", genreService.count());
        model.addAttribute("salleCount", salleService.count());
        model.addAttribute("seanceCount", seanceService.count());
        return "admin/dashboard";
    }
}
