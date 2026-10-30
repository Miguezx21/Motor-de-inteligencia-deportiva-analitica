package com.jempe.admin.service;
import com.jempe.admin.entity.PadreTutor;
import com.jempe.admin.repository.PadreTutorRepository;
import com.jempe.admin.util.CedulaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class PadreTutorService {
    private final PadreTutorRepository repo;
    private final CedulaValidator cedulaValidator;

    public List<PadreTutor> findAll() { return repo.findAll(); }
    public Optional<PadreTutor> findById(Long id) { return repo.findById(id); }

    public PadreTutor save(PadreTutor p) {
        cedulaValidator.validarOLanzar(p.getCedula());
        if (p.getId() == null && repo.existsByCedula(p.getCedula()))
            throw new IllegalArgumentException("Ya existe un padre/tutor con esa cédula: " + p.getCedula());
        return repo.save(p);
    }

    public void deleteById(Long id) { repo.deleteById(id); }
}
