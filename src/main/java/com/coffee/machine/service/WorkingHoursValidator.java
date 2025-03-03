package com.coffee.machine.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * Сервис для проверки рабочего времени кофемашины.
 * Учитывает рабочие часы, выходные и праздничные дни.
 */
@Service
public class WorkingHoursValidator {

    private static final String HOLIDAY_API_URL = "https://date.nager.at/Api/v2/PublicHolidays/%d/%s";
    private static final String COUNTRY_CODE = "KZ"; // Казахстан, поменяйте при необходимости

    private final RestTemplate restTemplate;

    public WorkingHoursValidator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Проверяет, находится ли текущее время в рабочем интервале.
     * Рабочее время: 8:00 - 17:00, без выходных и праздников.
     *
     * @return {@code true}, если кофемашина может работать, иначе {@code false}
     */
    public boolean isWorkingHours() {
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        // Проверяем рабочие часы (8:00 - 17:00)
        if (now.isBefore(LocalTime.of(8, 0)) || now.isAfter(LocalTime.of(17, 0))) {
            return false;
        }

        // Проверяем выходные (суббота, воскресенье)
        if (today.getDayOfWeek().getValue() >= 6) {
            return false;
        }

        // Проверяем праздники (используем кэш)
        return !isPublicHoliday(today);
    }

    /**
     * Проверяет, является ли указанная дата праздником.
     * Использует API {@code date.nager.at} для получения списка праздничных дней.
     * Результаты кешируются для оптимизации запросов.
     *
     * @param date дата для проверки
     * @return {@code true}, если день является праздничным, иначе {@code false}
     */
    @Cacheable("holidays")
    public boolean isPublicHoliday(LocalDate date) {
        int year = date.getYear();
        String url = String.format(HOLIDAY_API_URL, year, COUNTRY_CODE);

        try {
            Holiday[] holidays = restTemplate.getForObject(url, Holiday[].class);
            List<LocalDate> holidayDates = Arrays.stream(holidays)
                    .map(h -> LocalDate.parse(h.date()))
                    .toList();
            return holidayDates.contains(date);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Если API не отвечает, считаем день рабочим
        }
    }

    /**
     * Вспомогательный класс для обработки данных API праздников.
     */
    private record Holiday(String date) {}
}
