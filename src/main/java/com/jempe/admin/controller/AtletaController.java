package com.jempe.admin.controller;

import com.jempe.admin.entity.Atleta;
import com.jempe.admin.service.AtletaService;
import com.jempe.admin.service.CategoriaService;
import com.jempe.admin.service.PadreTutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequiredArgsConstructor
@RequestMapping("/atletas")
public class AtletaController {
    private final AtletaService service;
    private final CategoriaService categoriaService;
    private final PadreTutorService padreTutorService;

    private void populateForm(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("padres", padreTutorService.findAll());
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("atletas", service.findAll());
        return "atleta/list";
    }

    @GetMapping("/nuevo")
    public String form(Model model) {
        model.addAttribute("atleta", new Atleta());
        populateForm(model);
        return "atleta/form";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return service.findById(id).map(a -> {
            model.addAttribute("atleta", a);
            populateForm(model);
            return "atleta/form";
        }).orElseGet(() -> { ra.addFlashAttribute("error","Atleta no encontrado"); return "redirect:/atletas"; });
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("atleta") Atleta atleta, BindingResult br,
                       Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            populateForm(model);
            return "atleta/form";
        }
        try {
            service.save(atleta);
            ra.addFlashAttribute("exito", "Atleta guardado. Cédula validada correctamente (Módulo 10).");
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            populateForm(model);
            return "atleta/form";
        }
        return "redirect:/atletas";
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.deleteById(id);
        ra.addFlashAttribute("exito", "Atleta eliminado.");
        return "redirect:/atletas";
    }
}
