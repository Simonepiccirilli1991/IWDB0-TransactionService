package com.iwbd0.saga.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iwbd0.saga.entity.Prodotti;

public interface ProdottiRepo extends JpaRepository<Prodotti, Long>{

	Prodotti findByCodiceProdotto(String codice);
	
	
}
