package com.jempe.admin.repository;
import com.jempe.admin.entity.PadreTutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PadreTutorRepository extends JpaRepository<PadreTutor, Long> {
    Optional<PadreTutor> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
}
