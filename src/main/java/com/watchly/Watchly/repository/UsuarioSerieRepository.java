package com.watchly.Watchly.repository;

import com.watchly.Watchly.model.UsuarioSerieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioSerieRepository extends JpaRepository<UsuarioSerieEntity, Long> {

    Optional<UsuarioSerieEntity> findByUsuarioIdAndSerieId(Long usuarioId, Long serieId);

    List<UsuarioSerieEntity> findByUsuarioId(Long usuarioId);
}