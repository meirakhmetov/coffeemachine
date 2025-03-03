package com.coffee.machine.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
/**
 * Конфигурационный класс для настройки кэширования с использованием Caffeine.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Создаёт и настраивает бин CacheManager для управления кэшем.
     *
     * @return экземпляр CaffeineCacheManager с настройками кэширования
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS) // Время жизни кэша - 1 час
                .maximumSize(100) // Максимальный размер кэша - 100 записей
        );
        return cacheManager;
    }
}
