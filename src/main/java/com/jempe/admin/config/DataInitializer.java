package com.jempe.admin.config;

import com.jempe.admin.entity.*;
import com.jempe.admin.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component @Slf4j @RequiredArgsConstructor
@Profile({"default", "dev"})
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final PadreTutorRepository padreTutorRepository;
    private final AtletaRepository atletaRepository;
    private final SesionRepository sesionRepository;

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() > 0) {
            log.info("DataInitializer: datos ya existen, omitiendo seed.");
            return;
        }
        log.info("DataInitializer: cargando datos de demostración...");

        // Categorías WSA (World Skate America) — Clubes Mayores 2026
        Categoria prejuvenil = new Categoria();
        prejuvenil.setNombre("Prejuvenil");
        prejuvenil.setRangoEdad("14 años");
        prejuvenil.setDescripcion("Categoría Prejuvenil WSA — 14 años");

        Categoria juvenil = new Categoria();
        juvenil.setNombre("Juvenil");
        juvenil.setRangoEdad("15-17 años");
        juvenil.setDescripcion("Categoría Juvenil WSA — 15 a 17 años");

        Categoria mayores = new Categoria();
        mayores.setNombre("Mayores");
        mayores.setRangoEdad("18+ años");
        mayores.setDescripcion("Categoría Mayores WSA — 18 años en adelante");

        categoriaRepository.saveAll(List.of(prejuvenil, juvenil, mayores));

        Entrenador alexis = new Entrenador();
        alexis.setCedula("1723456789");
        alexis.setNombre("Alexis");
        alexis.setApellido("Pérez");
        alexis.setCorreo("alexis.perez@jempe.ec");
        alexis.setTelefono("+593 99 123 4567");
        alexis.setEspecialidad("Velocidad y Sprint");
        alexis.setActivo(true);
        entrenadorRepository.save(alexis);

        PadreTutor papa = new PadreTutor();
        papa.setCedula("1756789012");
        papa.setNombre("Carlos");
        papa.setApellido("Torres");
        papa.setParentesco("Padre");
        papa.setCorreo("carlos.torres@example.com");
        papa.setTelefono("+593 98 765 4321");
        padreTutorRepository.save(papa);

        Atleta a1 = crearAtleta("1745678901", "Sofía",     "Mendoza", juvenil,    papa,  "Velocidad", LocalDate.of(2010, 3, 15));
        Atleta a2 = crearAtleta("1767890123", "Diego",     "Ruiz",    juvenil,    null,  "Fondo",     LocalDate.of(2009, 7, 22));
        Atleta a3 = crearAtleta("1789012345", "Valentina", "López",   mayores,    null,  "Velocidad", LocalDate.of(2005, 1, 10));
        atletaRepository.saveAll(List.of(a1, a2, a3));

        Sesion sesion = new Sesion();
        sesion.setNombre("Entrenamiento Sprint – Semana 1");
        sesion.setEntrenador(alexis);
        sesion.setFechaInicio(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0));
        sesion.setFechaFin(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0));
        sesion.setUbicacion("Pista principal JEMPE – Quito");
        sesion.setEstado("PLANIFICADA");
        sesion.setAtletas(List.of(a1, a2));
        sesionRepository.save(sesion);

        log.info("Seed OK → {} categorías, {} atletas, 1 sesión",
                categoriaRepository.count(), atletaRepository.count());
    }

    private Atleta crearAtleta(String cedula, String nombre, String apellido,
                                Categoria categoria, PadreTutor padre,
                                String especialidad, LocalDate nacimiento) {
        Atleta a = new Atleta();
        a.setCedula(cedula); a.setNombre(nombre); a.setApellido(apellido);
        a.setCategoria(categoria); a.setPadreTutor(padre);
        a.setEspecialidad(especialidad);
        a.setFechaNacimiento(nacimiento);
        a.setActivo(true);
        return a;
    }
}
