package com.watchly.Watchly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "temporada",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_temporada",
                        columnNames = {"serie_id", "numero"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "serie_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_temporada_serie")
    )
    private SerieEntity serie;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "media_avaliacao", precision = 3, scale = 2)
    private BigDecimal mediaAvaliacao;

    @OneToMany(
            mappedBy = "temporada",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<EpisodioEntity> episodios = new HashSet<>();
}