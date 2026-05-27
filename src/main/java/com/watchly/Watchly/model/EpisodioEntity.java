package com.watchly.Watchly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "episodio",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_episodio",
                        columnNames = {"temporada_id", "numero"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "temporada_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_episodio_temporada")
    )
    private TemporadaEntity temporada;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    @Column(name = "media_avaliacao", precision = 3, scale = 2)
    private BigDecimal mediaAvaliacao;
}