package com.watchly.Watchly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "filme_favorito",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "filme_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmeFavoritoEntity {

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

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}