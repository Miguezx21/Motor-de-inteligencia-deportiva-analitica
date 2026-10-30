package com.jempe.admin.repository;
import com.jempe.admin.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByEntrenadorId(Long entrenadorId);
    @Query("SELECT s FROM Sesion s JOIN FETCH s.entrenador ORDER BY s.fechaInicio DESC")
    List<Sesion> findAllConEntrenador();
}
