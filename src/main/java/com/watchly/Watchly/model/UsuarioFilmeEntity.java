package com.watchly.Watchly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_filme",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "filme_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioFilmeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    private FilmeEntity filme;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // "NAO_INICIADO" ou "ASSISTIDO"

    @Column(name = "adicionado_em")
    private LocalDateTime adicionadoEm;
}