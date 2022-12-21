package com.iwbd0.config;

import org.springframework.context.annotation.Bean;

import com.iwbd0.saga.model.request.AcquistiRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@org.springframework.context.annotation.Configuration
public class Configuration {
	
	@Bean
    public Sinks.Many<AcquistiRequest> sink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<AcquistiRequest> flux(Sinks.Many<AcquistiRequest> sink){
        return sink.asFlux();
    }

}
