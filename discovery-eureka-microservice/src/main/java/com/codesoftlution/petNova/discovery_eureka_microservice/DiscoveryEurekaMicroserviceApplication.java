package com.codesoftlution.petNova.discovery_eureka_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryEurekaMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryEurekaMicroserviceApplication.class, args);
	}

}
