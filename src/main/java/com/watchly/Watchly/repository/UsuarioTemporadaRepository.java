package com.watchly.Watchly.repository;

import com.watchly.Watchly.model.UsuarioTemporadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioTemporadaRepository extends JpaRepository<UsuarioTemporadaEntity, Long> {

    Optional<UsuarioTemporadaEntity> findByUsuarioIdAndTemporadaId(Long usuarioId, Long temporadaId);

    List<UsuarioTemporadaEntity> findByUsuarioId(Long usuarioId);
}