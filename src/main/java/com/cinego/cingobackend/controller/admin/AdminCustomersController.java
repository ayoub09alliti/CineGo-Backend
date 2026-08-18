package com.cinego.cingobackend.controller.admin;

import com.cinego.cingobackend.model.Customers;
import com.cinego.cingobackend.service.CustomersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/customers")
@RequiredArgsConstructor
public class AdminCustomersController {

    private final CustomersService customersService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", customersService.getListAll());
        return "admin/customers/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customers());
        return "admin/customers/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customersService.get(id));
        return "admin/customers/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Customers customer) {
        customersService.save(customer);
        return "redirect:/admin/customers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        customersService.delete(id);
        return "redirect:/admin/customers";
    }
}