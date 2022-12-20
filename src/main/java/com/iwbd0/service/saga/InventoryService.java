package com.iwbd0.service.saga;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwbd0.saga.entity.Inventory;
import com.iwbd0.saga.entity.repo.InventoryRepo;
import com.iwbd0.saga.model.request.InventoryRequest;
import com.iwbd0.saga.model.response.InventoryResponse;
import com.iwbd0.saga.model.response.ServiziResponse;

@Service
public class InventoryService {

	@Autowired
	InventoryRepo invRepo;
	@Autowired
	ServiziService servServ;
	
	//insert inventory
	public InventoryResponse insertInventory(InventoryRequest request) {

		InventoryResponse response = new InventoryResponse();

		Inventory dto = new Inventory();
		dto.setAmmontareDisponibile(request.getAmmontareDisponibile());
		dto.setCodiceServizio(request.getCodiceServizio());
		dto.setNome(request.getNome());

		try {
			response.setInventory(invRepo.save(dto));
		}catch(Exception e) {
			response.setError(true);
		}

		return response;

	}

	// get inventory
	public InventoryResponse getInventory(String nome) {

		InventoryResponse response = new InventoryResponse();

		List<Inventory> inventoryList = invRepo.findAll();

		Optional<Inventory> inventory = inventoryList.stream().filter(resp -> resp.getNome().equals(nome)).findAny();

		if(inventory.isEmpty())
			response.setError(true);
		else
			response.setInventory(inventory.get());

		return response;
	}

	//transaction inventory
	public InventoryResponse transactionInventory(InventoryRequest request) {

		InventoryResponse response = new InventoryResponse();

		Inventory inventory = null;
		try {
			inventory = invRepo.findBycodiceServizio(request.getCodiceServizio());
		}catch(Exception e) {
			response.setError(true);
			return response;
		}

		if(request.getQuantita() > inventory.getAmmontareDisponibile()) {
			response.setError(true);
			response.setMaxQuantity(inventory.getAmmontareDisponibile());
			return response;
		}
		// provo a recuperare il servizio da db per prendere il costo
		ServiziResponse servizio = null;
		try {
			servizio = servServ.findService(inventory.getCodiceServizio());
		}catch(Exception e) {
			response.setError(true);
			return response;
		}
		// vado a scalare a db
		int effettivo = inventory.getAmmontareDisponibile() - request.getQuantita();

		inventory.setAmmontareDisponibile(effettivo);
		invRepo.save(inventory);

		response.setAllowed(true);
		response.setCosto(servizio.getService().getCosto());

		return response;
	}

	//reverse transaction inventory
	public InventoryResponse rollBackTransactionInventory(InventoryRequest request) {

		InventoryResponse response = new InventoryResponse();

		Inventory inventory = null;
		try {
			inventory = invRepo.findBycodiceServizio(request.getCodiceServizio());
		}catch(Exception e) {
			response.setError(true);
			return response;
		}

		int effettivoRollback = request.getQuantita() + inventory.getAmmontareDisponibile();
		inventory.setAmmontareDisponibile(effettivoRollback);

		response.setInventory(invRepo.save(inventory));

		return response;
	}
}
