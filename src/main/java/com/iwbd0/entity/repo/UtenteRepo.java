package com.iwbd0.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iwbd0.model.entity.Utente;

public interface UtenteRepo extends JpaRepository<Utente, String>{

	@Query(value = "SELECT * FROM utente WHERE utente.username = :username",
			nativeQuery = true)
	Utente findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT * FROM utente WHERE utente.bt = :bt",
			nativeQuery = true)
	Utente findByBt(@Param("bt") String bt);
	
}
