package com.watchly.Watchly.repository;

import com.watchly.Watchly.model.FilmeFavoritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmeFavoritoRepository extends JpaRepository<FilmeFavoritoEntity, Long> {

    Optional<FilmeFavoritoEntity> findByUsuarioIdAndFilmeId(Long usuarioId, Long filmeId);

    List<FilmeFavoritoEntity> findByUsuarioId(Long usuarioId);
}