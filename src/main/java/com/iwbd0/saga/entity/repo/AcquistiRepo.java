package com.iwbd0.saga.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iwbd0.saga.entity.OrdineAcquisti;

@Repository
public interface AcquistiRepo extends JpaRepository<OrdineAcquisti, Long>{

	OrdineAcquisti findByBtAcquirente(String btAcquirente);
}
