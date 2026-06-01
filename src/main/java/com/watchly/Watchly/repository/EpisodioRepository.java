package com.watchly.Watchly.repository;

import com.watchly.Watchly.model.EpisodioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodioRepository extends JpaRepository<EpisodioEntity, Long> {

    List<EpisodioEntity> findByTemporadaId(Long temporadaId);
}