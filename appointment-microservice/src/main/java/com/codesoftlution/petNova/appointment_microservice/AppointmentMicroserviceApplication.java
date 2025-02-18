package com.codesoftlution.petNova.appointment_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AppointmentMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentMicroserviceApplication.class, args);
	}

}
