package com.watchly.Watchly.repository;

import com.watchly.Watchly.model.GeneroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<GeneroEntity, Long> {

    Optional<GeneroEntity> findByNome(String nome);
}