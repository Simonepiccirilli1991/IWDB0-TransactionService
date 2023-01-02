package com.iwbd0.saga.entity.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iwbd0.saga.entity.Ordini;

@Repository
public interface OrdiniRepo extends JpaRepository<Ordini, Long>{

	Optional<Ordini> findByBtAcquirente(String bt);
	
	Optional<Ordini> findByBtRicev(String bt);
}
