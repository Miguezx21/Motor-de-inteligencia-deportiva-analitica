package com.jempe.admin.service;
import com.jempe.admin.entity.Atleta;
import com.jempe.admin.entity.Sesion;
import com.jempe.admin.repository.AtletaRepository;
import com.jempe.admin.repository.SesionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class SesionService {
    private final SesionRepository repo;
    private final AtletaRepository atletaRepo;

    public List<Sesion> findAll() { return repo.findAllConEntrenador(); }
    public List<Sesion> findAllConRelaciones() { return repo.findAllConRelaciones(); }
    public Optional<Sesion> findById(Long id) { return repo.findById(id); }

    public Sesion save(Sesion sesion, List<Long> atletaIds) {
        if (atletaIds != null && !atletaIds.isEmpty()) {
            List<Atleta> atletas = atletaRepo.findAllById(atletaIds);
            sesion.setAtletas(atletas);
        } else {
            sesion.setAtletas(List.of());
        }
        return repo.save(sesion);
    }

    public void deleteById(Long id) { repo.deleteById(id); }
}
