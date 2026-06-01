package com.watchly.Watchly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_serie",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "serie_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSerieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id", nullable = false)
    private SerieEntity serie;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // "NAO_INICIADO", "INCOMPLETO" ou "ASSISTIDO"

    @Column(name = "adicionado_em")
    private LocalDateTime adicionadoEm;
}