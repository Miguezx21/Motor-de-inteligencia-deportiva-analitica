package com.jempe.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Data
@Table(name = "sesion")
public class Sesion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la sesión es obligatorio")
    @Column(nullable = false, length = 200)
    private String nombre;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(length = 200)
    private String ubicacion;

    @Column(length = 1000)
    private String observaciones;

    @Column(length = 20)
    private String estado = "PLANIFICADA";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrenador_id", nullable = false)
    private Entrenador entrenador;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "sesion_atleta",
        joinColumns = @JoinColumn(name = "sesion_id"),
        inverseJoinColumns = @JoinColumn(name = "atleta_id")
    )
    private List<Atleta> atletas;
}
