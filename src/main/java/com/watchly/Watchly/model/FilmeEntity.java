package com.watchly.Watchly.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "filme")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "url_poster")
    private String urlPoster;

    @Column(name = "media_avaliacao", precision = 3, scale = 2)
    private BigDecimal mediaAvaliacao;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @ManyToMany
    @JoinTable(
            name = "filme_genero",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private Set<GeneroEntity> generos = new HashSet<>();
}
