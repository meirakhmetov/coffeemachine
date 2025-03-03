package com.coffee.machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Главный класс Spring Boot приложения для управления кофемашиной.
 * Запускает приложение и включает кэширование.
 */
@SpringBootApplication
@EnableCaching
public class CoffeemachineApplication {

	/**
	 * Точка входа в приложение.
	 *
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		SpringApplication.run(CoffeemachineApplication.class, args);
	}

}
