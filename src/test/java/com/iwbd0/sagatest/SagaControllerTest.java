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
import com.iwbd0.saga.entity.repo.OrdiniRepo;
import com.iwbd0.saga.model.request.OrdiniRequest;
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
}
