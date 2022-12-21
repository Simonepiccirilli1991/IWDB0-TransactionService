package com.iwbd0.sagatest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.iwbd0.saga.entity.OrdineAcquisti;
import com.iwbd0.saga.entity.repo.AcquistiRepo;
import com.iwbd0.saga.model.request.AcquistiRequest;
import com.iwbd0.saga.model.request.InventoryRequest;
import com.iwbd0.saga.model.request.ServiziRequest;
import com.iwbd0.saga.model.response.InventoryResponse;
import com.iwbd0.saga.model.response.ServiziResponse;
import com.iwbd0.service.saga.AcquistiService;
import com.iwbd0.service.saga.InventoryService;
import com.iwbd0.service.saga.ServiziService;

@SpringBootTest
@AutoConfigureTestDatabase
public class SagaServiceTest {

	@Autowired
	InventoryService invServ;
	@Autowired
	ServiziService servServ;
	@Autowired
	AcquistiService acqService;
	@Autowired
	AcquistiRepo acqRepo;
	
	// inventory test
	@Test
	public void insertInventoryTestOK() {
		
		InventoryRequest request = new InventoryRequest();
		request.setQuantita(10);
		request.setCodiceServizio("00123");
		request.setNome("Digitali");
		
		InventoryResponse resp = invServ.insertInventory(request);
		
		System.out.println(resp.getInventory());
	}
	
	@Test
	public void doubleTestOk() {
		
		InventoryRequest request = new InventoryRequest();
		request.setAmmontareDisponibile(10);
		request.setCodiceServizio("00123");
		request.setNome("Digitali");
		
		InventoryResponse resp = invServ.insertInventory(request);
		
		ServiziRequest iRequest = new ServiziRequest();
		iRequest.setNomeServizio("ElPuerco");
		iRequest.setCosto(200.00);
		iRequest.setCodice("00123");
		
		ServiziResponse iResp = servServ.insertService(iRequest);
		
		InventoryResponse finalResp = invServ.transactionInventory(request);
		
		System.out.println(finalResp);
		assertThat(finalResp.getAllowed()).isEqualTo(true);
		assertThat(finalResp.getCosto()).isEqualTo(200.00);
	}
	
	// acquire test
	@Test
	public void acquistiOrderServiceTestOK() {
		
		AcquistiRequest request = new AcquistiRequest();
		
		request.setBtAcquirente("btAcqui");
		request.setBtVenditore("btVendi");
		request.setCodiceProdotto("trxProd");
		
		OrdineAcquisti resp = acqService.generaOrdine(request);
		
		System.out.println(resp);
		assertThat(resp.getStatus()).isEqualTo("INIT");
		
		
		OrdineAcquisti queryResp = acqRepo.findByBtAcquirente("btAcqui");
		
		assertThat(queryResp.getCodiceProdotto()).isEqualTo("trxProd");
		
	}
	
	
	
}
