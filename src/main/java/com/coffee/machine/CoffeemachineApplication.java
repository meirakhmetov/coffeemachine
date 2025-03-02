package com.coffee.machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CoffeemachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeemachineApplication.class, args);
	}

}
