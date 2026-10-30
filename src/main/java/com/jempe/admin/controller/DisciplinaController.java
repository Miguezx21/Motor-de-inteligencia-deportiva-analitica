package com.jempe.admin.controller;

import com.jempe.admin.entity.Disciplina;
import com.jempe.admin.service.CategoriaService;
import com.jempe.admin.service.DisciplinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequiredArgsConstructor
@RequestMapping("/disciplinas")
public class DisciplinaController {
    private final DisciplinaService service;
    private final CategoriaService categoriaService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("disciplinas", service.findAll());
        return "disciplina/list";
    }

    @GetMapping("/nueva")
    public String form(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        model.addAttribute("categorias", categoriaService.findAll());
        return "disciplina/form";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return service.findById(id).map(d -> {
            model.addAttribute("disciplina", d);
            model.addAttribute("categorias", categoriaService.findAll());
            return "disciplina/form";
        }).orElseGet(() -> { ra.addFlashAttribute("error","Disciplina no encontrada"); return "redirect:/disciplinas"; });
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("disciplina") Disciplina d, BindingResult br,
                       Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            model.addAttribute("categorias", categoriaService.findAll());
            return "disciplina/form";
        }
        service.save(d);
        ra.addFlashAttribute("exito", "Disciplina guardada correctamente.");
        return "redirect:/disciplinas";
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.deleteById(id);
        ra.addFlashAttribute("exito", "Disciplina eliminada.");
        return "redirect:/disciplinas";
    }
}
