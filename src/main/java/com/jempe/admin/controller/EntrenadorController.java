package com.jempe.admin.controller;

import com.jempe.admin.entity.Entrenador;
import com.jempe.admin.service.EntrenadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequiredArgsConstructor
@RequestMapping("/entrenadores")
public class EntrenadorController {
    private final EntrenadorService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("entrenadores", service.findAll());
        return "entrenador/list";
    }

    @GetMapping("/nuevo")
    public String form(Model model) {
        model.addAttribute("entrenador", new Entrenador());
        return "entrenador/form";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return service.findById(id).map(e -> {
            model.addAttribute("entrenador", e);
            return "entrenador/form";
        }).orElseGet(() -> { ra.addFlashAttribute("error","Entrenador no encontrado"); return "redirect:/entrenadores"; });
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("entrenador") Entrenador e, BindingResult br,
                       Model model, RedirectAttributes ra) {
        if (br.hasErrors()) return "entrenador/form";
        try {
            service.save(e);
            ra.addFlashAttribute("exito", "Entrenador guardado correctamente.");
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "entrenador/form";
        }
        return "redirect:/entrenadores";
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.deleteById(id);
        ra.addFlashAttribute("exito", "Entrenador eliminado.");
        return "redirect:/entrenadores";
    }
}
