package com.iwbd0.saga.entity.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iwbd0.saga.entity.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Long>{

	@Query( value = "SELECT inventory WHERE nome\\=:nome", nativeQuery = true)
	@Modifying
	Optional<Inventory> findByName(@Param("nome") String nome);
	
	@Query( value = "SELECT inventory WHERE codiceServizio\\=:codiceServizio", nativeQuery = true)
	@Modifying
	Inventory findBycodiceServizio(@Param("codiceServizio") String codiceServizio);
}
