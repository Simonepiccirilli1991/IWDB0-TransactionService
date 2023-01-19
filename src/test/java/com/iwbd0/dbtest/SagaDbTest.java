package com.iwbd0.dbtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.iwbd0.saga.entity.Ordini;
import com.iwbd0.saga.entity.Prodotti;
import com.iwbd0.saga.entity.repo.OrdiniRepo;
import com.iwbd0.saga.entity.repo.ProdottiRepo;
import com.iwbd0.saga.model.request.OrdiniRequest;
import com.iwbd0.saga.model.request.ProdottiRequest;
import com.iwbd0.saga.model.request.StatusRequest;
import com.iwbd0.saga.model.response.OrdiniResponse;
import com.iwbd0.saga.model.response.ProdottiResponse;
import com.iwbd0.service.saga.OrdiniService;
import com.iwbd0.service.saga.ProdottiService;
import com.iwbd0.util.OrcClient;

@SpringBootTest
@AutoConfigureTestDatabase
public class SagaDbTest {

	@MockBean
	OrcClient orcClient;
	@Autowired
	OrdiniRepo ordiniRepo;
	@Autowired
	ProdottiRepo prodottiRepo;
	@Autowired
	OrdiniService ordService;
	@Autowired
	ProdottiService prodService;
	
	
	@Test
	public void insertECheck() {
		
		Ordini ordine = new Ordini();
		ordine.setBtAcquirente("btAcquisto");
		ordine.setBtRicev("btRicevent");
		ordine.setCodiceProd("1111");
		ordine.setCosto(60.00);
		
		Ordini response = ordiniRepo.save(ordine);
		
		System.out.println(response);
		
		OrdiniRequest request = new OrdiniRequest();
		request.setBtAcquirente("btAcquisto");
		request.setOrderNumber((long) 1);
		OrdiniResponse iresp = ordService.getOrderById(request);
		
		assertThat(iresp.getOrdine().getCosto()).isEqualTo(60.00);
		assertThat(iresp.getOrdine().getCodiceProd()).isEqualTo("1111");
		
		
	}
	
	@Test
	public void insertEUpdateTestOK() {
		
		OrdiniRequest ordine = new OrdiniRequest();
		ordine.setBtAcquirente("btAcquisto55");
		ordine.setBtRicev("btRicevent");
		ordine.setCodiceProd("1111");
		ordine.setCosto(60.00);
		
		OrdiniResponse resp = ordService.creaOrdine(ordine);
		
		System.out.println(resp.getOrdine().getId());
		
		StatusRequest request = new StatusRequest();
		request.setTrxId(resp.getOrdine().getId());
		request.setStatus("COMPLETE");
		
		ordService.updateStatus(request);
		
		Optional<Ordini> response = ordiniRepo.findByBtAcquirente("btAcquisto55");
		
		assertThat(response.get().getStatus()).isEqualTo("COMPLETE");
	}
	
	// prodotti test
	
	@Test
	public void TransactionTestOK() {

		ProdottiRequest request = new ProdottiRequest();
		request.setAviableService(10);
		request.setCodiceProdotto("aaaa1");
		
		prodService.insertProdotto(request);
		
		prodService.transactionProd(request);

		Prodotti prod = prodottiRepo.findByCodiceProdotto("aaaa1");

		assertThat(prod.getAviableService()).isEqualTo(9);
	}
		
	@Test
	public void insertProdottoTestOK() {
		
		ProdottiRequest request = new ProdottiRequest();
		request.setAviableService(10);
		request.setCodiceProdotto("aaaa2");
		
		prodService.insertProdotto(request);
		
		Prodotti prod = prodottiRepo.findByCodiceProdotto("aaaa2");
		
		assertThat(prod.getAviableService()).isEqualTo(10);
		
	}
	
	@Test
	public void transactionERollbackTestOK() {
		
		ProdottiRequest request = new ProdottiRequest();
		request.setAviableService(10);
		request.setCodiceProdotto("aaaa3");
		
		prodService.insertProdotto(request);
		
		prodService.transactionProd(request);

		Prodotti prod = prodottiRepo.findByCodiceProdotto("aaaa3");

		assertThat(prod.getAviableService()).isEqualTo(9);
		
		prodService.rollbackProd(request);
		
		prod = prodottiRepo.findByCodiceProdotto("aaaa3");

		assertThat(prod.getAviableService()).isEqualTo(10);
	}
	
}
