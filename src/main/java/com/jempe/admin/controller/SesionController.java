package com.jempe.admin.controller;

import com.jempe.admin.entity.Sesion;
import com.jempe.admin.repository.SesionRepository;
import com.jempe.admin.service.AtletaService;
import com.jempe.admin.service.EntrenadorService;
import com.jempe.admin.service.SesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller @RequiredArgsConstructor
@RequestMapping("/sesiones")
public class SesionController {
    private final SesionService service;
    private final EntrenadorService entrenadorService;
    private final AtletaService atletaService;
    private final SesionRepository sesionRepository;

    private void populateForm(Model model) {
        model.addAttribute("entrenadores", entrenadorService.findAll());
        model.addAttribute("atletasActivos", atletaService.findAllActivos());
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("sesiones", service.findAllConRelaciones());
        return "sesion/list";
    }

    @GetMapping("/nueva")
    public String form(Model model) {
        model.addAttribute("sesion", new Sesion());
        populateForm(model);
        return "sesion/form";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return sesionRepository.findByIdConAtletas(id).map(s -> {
            model.addAttribute("sesion", s);
            populateForm(model);
            return "sesion/form";
        }).orElseGet(() -> { ra.addFlashAttribute("error","Sesión no encontrada"); return "redirect:/sesiones"; });
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("sesion") Sesion sesion, BindingResult br,
                       @RequestParam(value = "atletaIds", required = false) List<Long> atletaIds,
                       Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            populateForm(model);
            return "sesion/form";
        }
        service.save(sesion, atletaIds);
        ra.addFlashAttribute("exito", "Sesión guardada correctamente.");
        return "redirect:/sesiones";
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.deleteById(id);
        ra.addFlashAttribute("exito", "Sesión eliminada.");
        return "redirect:/sesiones";
    }
}
