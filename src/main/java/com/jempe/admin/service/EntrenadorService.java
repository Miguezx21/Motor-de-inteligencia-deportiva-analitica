package com.jempe.admin.service;
import com.jempe.admin.entity.Entrenador;
import com.jempe.admin.repository.EntrenadorRepository;
import com.jempe.admin.util.CedulaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class EntrenadorService {
    private final EntrenadorRepository repo;
    private final CedulaValidator cedulaValidator;

    public List<Entrenador> findAll() { return repo.findAll(); }
    public Optional<Entrenador> findById(Long id) { return repo.findById(id); }

    public Entrenador save(Entrenador e) {
        cedulaValidator.validarOLanzar(e.getCedula());
        if (e.getId() == null && repo.existsByCedula(e.getCedula()))
            throw new IllegalArgumentException("Ya existe un entrenador con esa cédula: " + e.getCedula());
        return repo.save(e);
    }

    public void deleteById(Long id) { repo.deleteById(id); }
}
