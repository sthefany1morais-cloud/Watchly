package com.watchly.Watchly.repository;

import com.watchly.Watchly.model.TemporadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemporadaRepository extends JpaRepository<TemporadaEntity, Long> {

    Optional<TemporadaEntity> findBySerieIdAndNumero(Long serieId, Integer numero);

    List<TemporadaEntity> findBySerieId(Long serieId);
}