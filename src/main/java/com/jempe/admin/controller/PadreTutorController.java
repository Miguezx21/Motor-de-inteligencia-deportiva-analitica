package com.jempe.admin.controller;

import com.jempe.admin.entity.PadreTutor;
import com.jempe.admin.service.PadreTutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequiredArgsConstructor
@RequestMapping("/padres")
public class PadreTutorController {
    private final PadreTutorService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("padres", service.findAll());
        return "padre/list";
    }

    @GetMapping("/nuevo")
    public String form(Model model) {
        model.addAttribute("padreTutor", new PadreTutor());
        return "padre/form";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return service.findById(id).map(p -> {
            model.addAttribute("padreTutor", p);
            return "padre/form";
        }).orElseGet(() -> { ra.addFlashAttribute("error","Padre/Tutor no encontrado"); return "redirect:/padres"; });
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("padreTutor") PadreTutor p, BindingResult br,
                       Model model, RedirectAttributes ra) {
        if (br.hasErrors()) return "padre/form";
        try {
            service.save(p);
            ra.addFlashAttribute("exito", "Padre/Tutor guardado correctamente.");
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "padre/form";
        }
        return "redirect:/padres";
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.deleteById(id);
        ra.addFlashAttribute("exito", "Padre/Tutor eliminado.");
        return "redirect:/padres";
    }
}
