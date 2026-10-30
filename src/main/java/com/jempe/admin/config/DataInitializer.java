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
    private final DisciplinaRepository disciplinaRepository;
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

        Categoria subInfantil = new Categoria();
        subInfantil.setNombre("Sub-10");
        subInfantil.setRangoEdad("8-10 años");
        subInfantil.setDescripcion("Categoría infantil – iniciación al patinaje de velocidad");

        Categoria subJuvenil = new Categoria();
        subJuvenil.setNombre("Sub-16");
        subJuvenil.setRangoEdad("13-16 años");
        subJuvenil.setDescripcion("Categoría juvenil – formación técnico-táctica");

        Categoria elite = new Categoria();
        elite.setNombre("Elite");
        elite.setRangoEdad("17+ años");
        elite.setDescripcion("Categoría élite – competencia de alto rendimiento");

        categoriaRepository.saveAll(List.of(subInfantil, subJuvenil, elite));

        Disciplina d300    = crearDisc("300m Sprint",   "Prueba corta de velocidad",       subInfantil);
        Disciplina d500    = crearDisc("500m",          "Prueba clásica de velocidad",      subJuvenil);
        Disciplina d1000   = crearDisc("1000m",         "Prueba de fondo medio",            subJuvenil);
        Disciplina d5000   = crearDisc("5000m Puntos",  "Prueba de puntos – élite",         elite);
        Disciplina maraton = crearDisc("Maratón",       "42km – máxima exigencia aeróbica", elite);
        disciplinaRepository.saveAll(List.of(d300, d500, d1000, d5000, maraton));

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

        Atleta a1 = crearAtleta("1745678901", "Sofía",    "Mendoza", d500,  papa);
        Atleta a2 = crearAtleta("1767890123", "Diego",    "Ruiz",    d1000, null);
        Atleta a3 = crearAtleta("1789012345", "Valentina","López",   d5000, null);
        atletaRepository.saveAll(List.of(a1, a2, a3));

        Sesion sesion = new Sesion();
        sesion.setNombre("Entrenamiento Sprint – Semana 1");
        sesion.setEntrenador(alexis);
        sesion.setFechaInicio(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0));
        sesion.setFechaFin(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0));
        sesion.setUbicacion("Pista principal JEMPE – Quito");
        sesion.setEstado("PLANIFICADA");
        sesion.setAtletas(List.of(a1, a2));
        sesionRepository.save(sesion);

        log.info("Seed OK → {} categorías, {} disciplinas, {} atletas, 1 sesión",
                categoriaRepository.count(), disciplinaRepository.count(), atletaRepository.count());
    }

    private Disciplina crearDisc(String nombre, String desc, Categoria cat) {
        Disciplina d = new Disciplina();
        d.setNombre(nombre); d.setDescripcion(desc); d.setCategoria(cat);
        return d;
    }

    private Atleta crearAtleta(String cedula, String nombre, String apellido,
                                Disciplina disc, PadreTutor padre) {
        Atleta a = new Atleta();
        a.setCedula(cedula); a.setNombre(nombre); a.setApellido(apellido);
        a.setDisciplina(disc); a.setPadreTutor(padre);
        a.setFechaNacimiento(LocalDate.of(2008, 3, 15));
        a.setActivo(true);
        return a;
    }
}
