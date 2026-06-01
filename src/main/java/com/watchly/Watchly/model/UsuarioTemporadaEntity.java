package com.watchly.Watchly.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_temporada",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "temporada_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioTemporadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temporada_id", nullable = false)
    private TemporadaEntity temporada;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // "NAO_INICIADO", "INCOMPLETO" ou "ASSISTIDO"

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}