package com.coffee.machine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * Конфигурационный класс для определения бинов приложения.
 */
@Configuration
public class AppConfig {
    /**
     * Создаёт и настраивает бин RestTemplate для выполнения HTTP-запросов.
     *
     * @return экземпляр RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
