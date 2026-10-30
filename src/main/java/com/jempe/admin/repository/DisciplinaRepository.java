package com.jempe.admin.repository;
import com.jempe.admin.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByCategoriaId(Long categoriaId);
}
