package com.jempe.admin.controller;

import com.jempe.admin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AtletaRepository atletaRepo;
    private final EntrenadorRepository entrenadorRepo;
    private final SesionRepository sesionRepo;
    private final CategoriaRepository categoriaRepo;

    @GetMapping("/")
    public String root() { return "redirect:/dashboard"; }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalAtletas",     atletaRepo.count());
        model.addAttribute("totalEntrenadores", entrenadorRepo.count());
        model.addAttribute("totalSesiones",    sesionRepo.count());
        model.addAttribute("totalCategorias",  categoriaRepo.count());
        return "dashboard";
    }
}
