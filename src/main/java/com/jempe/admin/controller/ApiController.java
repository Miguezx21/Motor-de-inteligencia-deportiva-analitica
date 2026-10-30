package com.jempe.admin.controller;

import com.jempe.admin.entity.Atleta;
import com.jempe.admin.entity.Sesion;
import com.jempe.admin.repository.DisciplinaRepository;
import com.jempe.admin.repository.SesionRepository;
import com.jempe.admin.service.AtletaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST endpoints que alimentan los cascading dropdowns vía AJAX (cascading.js).
 *
 * GET /api/disciplinas?categoriaId=X  → lista disciplinas de esa categoría
 * GET /api/sesiones-por-entrenador?entrenadorId=X
 * GET /api/atletas-por-sesion?sesionId=X
 * GET /api/atletas-activos
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final DisciplinaRepository disciplinaRepo;
    private final SesionRepository sesionRepo;
    private final AtletaService atletaService;

    @GetMapping("/disciplinas")
    public List<Map<String, Object>> disciplinasPorCategoria(@RequestParam Long categoriaId) {
        return disciplinaRepo.findByCategoriaId(categoriaId).stream()
                .map(d -> Map.<String, Object>of("id", d.getId(), "nombre", d.getNombre()))
                .toList();
    }

    @GetMapping("/sesiones-por-entrenador")
    public List<Map<String, Object>> sesionesPorEntrenador(@RequestParam Long entrenadorId) {
        return sesionRepo.findByEntrenadorId(entrenadorId).stream()
                .map(s -> Map.<String, Object>of("id", s.getId(), "nombre", s.getNombre()))
                .toList();
    }

    @GetMapping("/atletas-por-sesion")
    public List<Map<String, Object>> atletasPorSesion(@RequestParam Long sesionId) {
        return sesionRepo.findById(sesionId)
                .map(Sesion::getAtletas)
                .orElse(List.of())
                .stream()
                .map(a -> Map.<String, Object>of(
                        "id", a.getId(),
                        "nombre", a.getNombre() + " " + a.getApellido()))
                .toList();
    }

    @GetMapping("/atletas-activos")
    public List<Map<String, Object>> atletasActivos() {
        return atletaService.findAllActivos().stream()
                .map(a -> Map.<String, Object>of(
                        "id", a.getId(),
                        "nombre", a.getNombre() + " " + a.getApellido()))
                .toList();
    }
}
