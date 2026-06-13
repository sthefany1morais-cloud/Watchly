package com.watchly.Watchly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "serie_favorita",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "serie_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SerieFavoritaEntity {

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

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}