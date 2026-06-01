package com.watchly.Watchly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_episodio",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "episodio_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEpisodioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episodio_id", nullable = false)
    private EpisodioEntity episodio;

    @Column(name = "assistido")
    private Boolean assistido;

    @Column(name = "assistido_em")
    private LocalDateTime assistidoEm;
}