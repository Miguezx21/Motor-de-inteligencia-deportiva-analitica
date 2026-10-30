package com.jempe.admin.service;
import com.jempe.admin.entity.Disciplina;
import com.jempe.admin.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class DisciplinaService {
    private final DisciplinaRepository repo;
    public List<Disciplina> findAll() { return repo.findAll(); }
    public Optional<Disciplina> findById(Long id) { return repo.findById(id); }
    public Disciplina save(Disciplina d) { return repo.save(d); }
    public void deleteById(Long id) { repo.deleteById(id); }
    public List<Disciplina> findByCategoriaId(Long catId) { return repo.findByCategoriaId(catId); }
}
