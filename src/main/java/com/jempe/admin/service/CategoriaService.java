package com.jempe.admin.service;
import com.jempe.admin.entity.Categoria;
import com.jempe.admin.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository repo;
    public List<Categoria> findAll() { return repo.findAll(); }
    public Optional<Categoria> findById(Long id) { return repo.findById(id); }
    public Categoria save(Categoria c) { return repo.save(c); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
