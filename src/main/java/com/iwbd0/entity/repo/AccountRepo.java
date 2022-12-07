package com.iwbd0.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.iwbd0.model.entity.Account;
import com.iwbd0.model.entity.Utente;


public interface AccountRepo extends JpaRepository<Account, Long>{

	void deleteByUtente(Utente utente);
	Account findByUtenteBt(String bt);
	void deleteByUtenteBt(String username);
	
	  @Query(value = """
		        UPDATE account
		        SET saldoattuale = :saldo
		        SET debito = :debito
		        WHERE utente_bte = :utente_bt
		        """,
		        nativeQuery = true)
		    @Modifying
		    @Transactional
		    int addBalanceDebit(@Param("utente_bt") String bt, @Param("saldo") double saldo, @Param("debito") Double debito);
	  
	  @Query(value = """
		        UPDATE account
		        SET saldoAttuale = :saldo
		        WHERE utente_bt = :utente_bt
		        """,
		        nativeQuery = true)
		    @Modifying
		    @Transactional
		    int addBalance(@Param("utente_bt") String username, @Param("saldo") double saldo);
	  
}
