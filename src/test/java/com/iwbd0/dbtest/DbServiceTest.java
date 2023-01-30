package com.iwbd0.dbtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.iwbd0.entity.repo.AccountRepo;
import com.iwbd0.entity.repo.UtenteRepo;
import com.iwbd0.model.entity.Account;
import com.iwbd0.model.entity.Utente;
import com.iwbd0.model.request.DispoRequest;
import com.iwbd0.service.dispo.DispoService;

@SpringBootTest
@AutoConfigureTestDatabase //h2
public class DbServiceTest {
	
	@Autowired
	DispoService dispoService;
	
	@Autowired
	UtenteRepo utenteRepo;
	@Autowired
	AccountRepo accRepo;
	
	@Test
	public void provadbOK() {
		
		Utente utente = new Utente();
		utente.setBt("blabla");
		utente.setChannel("test");
		utente.setUsername("user-test");
		
		//utenteRepo.save(utente);
		
		Utente secondUt = new Utente();
		secondUt.setBt("trty");
		secondUt.setChannel("test-4");
		secondUt.setUsername("user-test");
		
		List<Utente> listUt = new ArrayList<>();
		listUt.add(secondUt);
		listUt.add(utente);
		
		utenteRepo.saveAll(listUt);
		System.out.println(utenteRepo.findAll());
		Optional<Utente> ut = utenteRepo.findAll().stream().filter(resp -> resp.getBt().equals("blabla")).findAny();
		
		assertThat(ut.get().getChannel()).isEqualTo("test");
	}
	
	
	
	@Test
	public void testTransactionOK() throws InterruptedException {
		
		// setto dto pagatori
		Utente utenteToPay = new Utente();
		utenteToPay.setBt("utToPay");
		utenteToPay.setChannel("test1");
		utenteToPay.setUsername("user-test1");
		
		Utente utenteToReceive = new Utente();
		utenteToReceive.setBt("utToReceiuve");
		utenteToReceive.setChannel("test2");
		utenteToReceive.setUsername("user-test2");
		
		List<Utente> listUt = new ArrayList<>();
		listUt.add(utenteToReceive); listUt.add(utenteToPay);
		
		utenteRepo.saveAll(listUt);
		
		Account acc1 = new Account();
		acc1.setCodiceconto("consto-test1");
		acc1.setDebito(0.00);
		acc1.setSaldoattuale(100.00);
		acc1.setTipoConto("Debit");
		acc1.setUtente(utenteToPay);
		
		Account acc2 = new Account();
		acc2.setCodiceconto("consto-test2");
		acc2.setDebito(0.00);
		acc2.setSaldoattuale(10.00);
		acc2.setTipoConto("Debit");
		acc2.setUtente(utenteToReceive);
		
		List<Account> listAcc = new ArrayList<>();
		
		listAcc.add(acc1); listAcc.add(acc2);
		
		accRepo.saveAll(listAcc);
		
//		Optional<Account> resp = accRepo.findAll().stream().filter(respo -> respo.getUtente().getBt().equals("utToPay")).findAny();
//		
//		assertThat(resp.get().getSaldoattuale()).isEqualTo(100.00);
		// settato db, ora faccio test su layer paralleli
		
		DispoRequest request = new DispoRequest();
		
		request.setBtToPay("utToPay");
		request.setBtToReceive("utToReceiuve");
		request.setImporto(50.00);
		
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(5);

		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					startLatch.await();

					dispoService.dispoPayService(request);
				} catch (Exception e) {
				} finally {
					endLatch.countDown();
				}
			}).start();
		}
		startLatch.countDown();
		endLatch.await();

		
		assertThat(accRepo.findByUtenteBt("utToReceiuve").getSaldoattuale()).isEqualTo(60.00);
	    assertThat(accRepo.findByUtenteBt("utToPay").getSaldoattuale()).isEqualTo(50.00);
	}

}
