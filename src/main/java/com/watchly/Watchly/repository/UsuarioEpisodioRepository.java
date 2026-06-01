package com.watchly.Watchly.repository;

import com.watchly.Watchly.model.UsuarioEpisodioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioEpisodioRepository extends JpaRepository<UsuarioEpisodioEntity, Long> {

    Optional<UsuarioEpisodioEntity> findByUsuarioIdAndEpisodioId(Long usuarioId, Long episodioId);
}