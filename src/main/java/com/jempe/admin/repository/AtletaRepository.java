package com.jempe.admin.repository;
import com.jempe.admin.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
public interface AtletaRepository extends JpaRepository<Atleta, Long> {
    Optional<Atleta> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
    List<Atleta> findByDisciplinaId(Long disciplinaId);
    List<Atleta> findByDisciplinaCategoriaId(Long categoriaId);
    @Query("SELECT a FROM Atleta a WHERE a.activo = true ORDER BY a.apellido, a.nombre")
    List<Atleta> findAllActivos();
}
