package com.jempe.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Entity @Data
@Table(name = "padre_tutor")
public class PadreTutor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La cédula es obligatoria")
    @Pattern(regexp = "\\d{10}", message = "La cédula debe tener 10 dígitos")
    @Column(unique = true, nullable = false, length = 10)
    private String cedula;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 50)
    private String parentesco;

    @Email(message = "Correo inválido")
    @Column(length = 150)
    private String correo;

    @Column(length = 20)
    private String telefono;

    @OneToMany(mappedBy = "padreTutor", fetch = FetchType.LAZY)
    private List<Atleta> atletas;
}
