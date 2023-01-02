package com.iwbd0.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.iwbd0.saga.model.request.OrchestratorRequest;
import com.iwbd0.saga.model.response.OrchestratorResponse;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Configuration
public class OrcClient {

	@Value("${config.orchestrator.end-point}")
	private String url;
	WebClient webClient = WebClient.create(url);
	
	
	public OrchestratorResponse sagaOrchestration(OrchestratorRequest request) {

		OrchestratorResponse iResp = new OrchestratorResponse();
		Mono<OrchestratorResponse> response = null;

		try {
			response = webClient.post()
					.uri("orc/do")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), OrchestratorRequest.class)
					.retrieve()
					.bodyToMono(OrchestratorResponse.class)
					.subscribeOn(Schedulers.parallel());

		}catch(Exception e) {

		}

		iResp = response.block();

		return iResp;
	}
}
