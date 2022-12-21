package com.iwbd0.saga.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iwbd0.saga.entity.Servizi;

@Repository
public interface ServiziRepo extends JpaRepository<Servizi, Long>{

	@Query(value = "SELECT servizi WHERE codiceServizio\\=:codiceServizio", nativeQuery = true)
	@Modifying
	Servizi findByName(@Param("codiceServizio") String codiceServizio);
	
}
