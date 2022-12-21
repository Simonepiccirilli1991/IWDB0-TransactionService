package com.iwbd0.saga.entity.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iwbd0.saga.entity.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long>{

	@Query( value = "SELECT * FROM inventory WHERE inventory.nome =:nome", nativeQuery = true)
	@Modifying
	Optional<Inventory> findByName(@Param("nome") String nome);
	
	@Query( value = "SELECT inventory WHERE codiceservizio = :codiceservizio", nativeQuery = true)
	@Modifying
	Inventory findByCodiceServizio(@Param("codiceservizio") String codiceservizio);
}
