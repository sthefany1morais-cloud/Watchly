package com.watchly.Watchly.repository;

import com.watchly.Watchly.model.UsuarioFilmeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioFilmeRepository extends JpaRepository<UsuarioFilmeEntity, Long> {

    Optional<UsuarioFilmeEntity> findByUsuarioIdAndFilmeId(Long usuarioId, Long filmeId);

    List<UsuarioFilmeEntity> findByUsuarioId(Long usuarioId);
}