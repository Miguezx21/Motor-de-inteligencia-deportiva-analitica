package com.jempe.admin.service;
import com.jempe.admin.entity.Atleta;
import com.jempe.admin.repository.AtletaRepository;
import com.jempe.admin.util.CedulaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class AtletaService {
    private final AtletaRepository repo;
    private final CedulaValidator cedulaValidator;

    public List<Atleta> findAll() { return repo.findAll(); }
    public Optional<Atleta> findById(Long id) { return repo.findById(id); }
    public List<Atleta> findAllActivos() { return repo.findAllActivos(); }
    public List<Atleta> findAllConRelaciones() { return repo.findAllConRelaciones(); }

    public Atleta save(Atleta atleta) {
        cedulaValidator.validarOLanzar(atleta.getCedula());
        if (atleta.getId() == null && repo.existsByCedula(atleta.getCedula()))
            throw new IllegalArgumentException("Ya existe un atleta registrado con la cédula: " + atleta.getCedula());
        return repo.save(atleta);
    }

    public void deleteById(Long id) { repo.deleteById(id); }
}
