package com.jempe.admin.controller;

import com.jempe.admin.entity.Categoria;
import com.jempe.admin.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequiredArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categorias", service.findAll());
        return "categoria/list";
    }

    @GetMapping("/nueva")
    public String form(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/form";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return service.findById(id).map(c -> {
            model.addAttribute("categoria", c);
            return "categoria/form";
        }).orElseGet(() -> { ra.addFlashAttribute("error","Categoría no encontrada"); return "redirect:/categorias"; });
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("categoria") Categoria c, BindingResult br,
                       RedirectAttributes ra, Model model) {
        if (br.hasErrors()) return "categoria/form";
        service.save(c);
        ra.addFlashAttribute("exito", "Categoría guardada correctamente.");
        return "redirect:/categorias";
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.deleteById(id);
        ra.addFlashAttribute("exito", "Categoría eliminada.");
        return "redirect:/categorias";
    }
}
