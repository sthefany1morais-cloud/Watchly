package com.watchly.Watchly.repository;

import com.watchly.Watchly.model.SerieFavoritaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SerieFavoritaRepository extends JpaRepository<SerieFavoritaEntity, Long> {

    Optional<SerieFavoritaEntity> findByUsuarioIdAndSerieId(Long usuarioId, Long serieId);

    List<SerieFavoritaEntity> findByUsuarioId(Long usuarioId);
}