package com.jempe.admin.repository;
import com.jempe.admin.entity.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
    Optional<Entrenador> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
}
