package com.jempe.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity @Data
@Table(name = "atleta")
public class Atleta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La cédula es obligatoria")
    @Pattern(regexp = "\\d{10}", message = "La cédula debe tener exactamente 10 dígitos")
    @Column(unique = true, nullable = false, length = 10)
    private String cedula;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false, length = 100)
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(length = 20)
    private String especialidad; // "Velocidad" o "Fondo" — opcional

    @Column(precision = 5)
    private Double peso;

    @Column(precision = 5)
    private Double talla;

    @Column(length = 20)
    private String lateralidad;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padre_tutor_id")
    private PadreTutor padreTutor;

    @ManyToMany(mappedBy = "atletas", fetch = FetchType.LAZY)
    private List<Sesion> sesiones;
}
