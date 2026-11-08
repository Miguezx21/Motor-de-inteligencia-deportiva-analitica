package com.jempe.admin.repository;
import com.jempe.admin.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByEntrenadorId(Long entrenadorId);
    @Query("SELECT s FROM Sesion s JOIN FETCH s.entrenador ORDER BY s.fechaInicio DESC")
    List<Sesion> findAllConEntrenador();
    @Query("SELECT DISTINCT s FROM Sesion s LEFT JOIN FETCH s.entrenador LEFT JOIN FETCH s.atletas ORDER BY s.fechaInicio DESC")
    List<Sesion> findAllConRelaciones();
    @Query("SELECT DISTINCT s FROM Sesion s LEFT JOIN FETCH s.entrenador LEFT JOIN FETCH s.atletas WHERE s.id = :id")
    Optional<Sesion> findByIdConAtletas(Long id);
}
