package com.iwbd0.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.iwbd0.saga.model.request.OrchestratorRequest;
import com.iwbd0.saga.model.response.OrchestratorResponse;

import reactor.core.publisher.Mono;

@Service
public class OrcClient {

	@Value("${config.orchestrator.end-point}")
	private String url;
	WebClient webClient = WebClient.create(url);
	
	@Async
	public OrchestratorResponse sagaOrchestration(OrchestratorRequest request) {

		OrchestratorResponse iResp = new OrchestratorResponse();
		Mono<OrchestratorResponse> response = null;

		String uri = UriComponentsBuilder.fromHttpUrl(url + "/sess/get").toUriString();
		try {
			response = webClient.post()
					.uri(uri)
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
