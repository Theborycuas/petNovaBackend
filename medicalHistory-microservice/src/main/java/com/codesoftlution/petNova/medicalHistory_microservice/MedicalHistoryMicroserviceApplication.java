package com.codesoftlution.petNova.medicalHistory_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedicalHistoryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalHistoryMicroserviceApplication.class, args);
	}

}
