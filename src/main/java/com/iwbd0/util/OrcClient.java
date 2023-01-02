package com.iwbd0.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.iwbd0.saga.model.request.OrchestratorRequest;
import com.iwbd0.saga.model.response.OrchestratorResponse;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class OrcClient {

	//@Value("${config.orchestrator.end-point}")
	private String url = "http://localhost:8089/";
	WebClient webClient = WebClient.create(url);
	
	@Async
	public OrchestratorResponse sagaOrchestration(OrchestratorRequest request) {

		OrchestratorResponse iResp = new OrchestratorResponse();
		Mono<OrchestratorResponse> response = null;

		try {
			response = webClient.post()
					.uri("orc/do")
					 .bodyValue(request)
					 .retrieve()
                     .bodyToMono(OrchestratorResponse.class)
                     .map(iresp -> {
                         // do something with the response
                         return iresp;
                       });

		}catch(Exception e) {

		}

		iResp = response.block();

		return iResp;
	}
}
