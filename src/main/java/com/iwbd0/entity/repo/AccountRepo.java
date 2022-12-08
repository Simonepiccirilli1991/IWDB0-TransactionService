package com.iwbd0.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iwbd0.model.entity.Account;
import com.iwbd0.model.entity.Utente;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long>{

	void deleteByUtente(Utente utente);
	Account findByUtenteBt(String bt);
	void deleteByUtenteBt(String username);
	
	@Query(value = """
			UPDATE account
			SET saldoattuale =:saldoattuale
		    debito =:debito
			WHERE codiceConto =:codiceconto
			""",
			nativeQuery = true)
	@Modifying
	@Transactional
	int addBalanceDebit(@Param("codiceconto") String codiceconto, @Param("saldoattuale") double saldoattuale, @Param("debito") Double debito);

	// funziona
//	@Query(value = """
//			UPDATE account
//			SET saldoattuale =:saldoattuale
//			WHERE codiceconto =:codiceconto
//			""",
//			nativeQuery = true)
	
	// funziona 
	@Query(value = "UPDATE account SET saldoattuale\\=:saldoattuale WHERE codiceconto\\=:codiceconto",
			nativeQuery = true)
	@Modifying
	@Transactional
	int addBalance(@Param("codiceconto") String codiceconto, @Param("saldoattuale") double saldoattuale);

}
