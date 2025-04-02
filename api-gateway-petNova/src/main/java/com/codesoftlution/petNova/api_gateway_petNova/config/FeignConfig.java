package com.codesoftlution.petNova.api_gateway_petNova.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class FeignConfig {

    @Bean
    public Scheduler feignScheduler() {
        return Schedulers.newBoundedElastic(
                10, // Tamaño máximo del pool
                100, // Límite de tareas en cola
                "feign-scheduler"
        );
    }

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters();
    }
}
