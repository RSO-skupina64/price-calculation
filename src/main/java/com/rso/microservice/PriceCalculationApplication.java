package com.rso.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class PriceCalculationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCalculationApplication.class, args);
	}

}
