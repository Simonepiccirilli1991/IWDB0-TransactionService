package com.iwbd0.sagatest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwbd0.saga.entity.Ordini;
import com.iwbd0.saga.entity.Prodotti;
import com.iwbd0.saga.entity.repo.OrdiniRepo;
import com.iwbd0.saga.entity.repo.ProdottiRepo;
import com.iwbd0.saga.model.request.OrdiniRequest;
import com.iwbd0.saga.model.request.ProdottiRequest;
import com.iwbd0.saga.model.response.OrdiniResponse;
import com.iwbd0.util.OrcClient;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class SagaControllerTest {

	@MockBean
	OrcClient orcClient;
	@Autowired
	MockMvc mvc;
	@Autowired
	OrdiniRepo ordiniRepo;
	@Autowired 
	ProdottiRepo prodRepo;
	
	ObjectMapper mapper = new ObjectMapper();
	
	// Ordini test
	@Test
	public void createOrderTestOK() throws Exception {
		
		OrdiniRequest ordine = new OrdiniRequest();
		ordine.setBtAcquirente("btAcquisto");
		ordine.setBtRicev("btRicevent");
		ordine.setCodiceProd("1111");
		ordine.setCosto(60.00);
		
		String response = mvc.perform(post("/ordini/insert")
				.contentType("application/json")
				.content(mapper.writeValueAsString(ordine)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		OrdiniResponse iResp = mapper.readValue(response, OrdiniResponse.class);
		
		assertThat(iResp.getOrdine().getStatus()).isEqualTo("CREATED");
	}
	
	@Test
	public void updateOrderTestOK() throws Exception {

		Ordini ordine = new Ordini();
		ordine.setBtAcquirente("btprovaA");
		ordine.setBtRicev("btprovaR");
		ordine.setCodiceProd("1111");
		ordine.setCosto(60.00);
		ordine.setStatus("CREATED");

		Ordini iRespS = ordiniRepo.save(ordine);
		//printline dell'id con cui viene salvato, al momento e 2 lascaire se si sminchia per vedere quale mette
		System.out.println(iRespS.getId());

		mvc.perform(put("/ordini/update/2/COMPLETE")
				.contentType("application/json"))
		.andExpect(status().isOk()).andReturn().getResponse();

		Optional<Ordini> resp = ordiniRepo.findByBtAcquirente("btprovaA");

		assertThat(resp.get().getStatus()).isEqualTo("COMPLETE");
		
	}
	
	// Prodotti test
	
	@Test
	public void TransactionTestOK() throws Exception {

		Prodotti prodotto = new Prodotti();
		prodotto.setAviableService(10);
		prodotto.setCodiceProdotto("aaaa1");

		prodRepo.save(prodotto);

		ProdottiRequest request = new ProdottiRequest();
		request.setAviableService(10);
		request.setCodiceProdotto("aaaa1");

		mvc.perform(post("/prodotti/transact")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
		.andExpect(status().isOk()).andReturn().getResponse();

		Prodotti prod = prodRepo.findByCodiceProdotto("aaaa1");

		assertThat(prod.getAviableService()).isEqualTo(9);
	}

	@Test
	public void insertProdottoTestOK() throws Exception {

		ProdottiRequest request = new ProdottiRequest();
		request.setAviableService(10);
		request.setCodiceProdotto("aaaa2");
		
		mvc.perform(put("/prodotti/insert")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
		.andExpect(status().isOk()).andReturn().getResponse();


		Prodotti prod = prodRepo.findByCodiceProdotto("aaaa2");

		assertThat(prod.getAviableService()).isEqualTo(10);

	}
	
	@Test
	public void transactionERollbackTestOK() throws Exception {
		
		ProdottiRequest request = new ProdottiRequest();
		request.setAviableService(10);
		request.setCodiceProdotto("aaaa3");
		
		Prodotti prodotto = new Prodotti();
		prodotto.setAviableService(10);
		prodotto.setCodiceProdotto("aaaa3");
		
		prodRepo.save(prodotto);
		
		mvc.perform(post("/prodotti/transact")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
		.andExpect(status().isOk()).andReturn().getResponse();

		Prodotti prod = prodRepo.findByCodiceProdotto("aaaa3");

		assertThat(prod.getAviableService()).isEqualTo(9);
		
		mvc.perform(post("/prodotti/rollb")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
		.andExpect(status().isOk()).andReturn().getResponse();
		
		prod = prodRepo.findByCodiceProdotto("aaaa3");

		assertThat(prod.getAviableService()).isEqualTo(10);
	}
}
