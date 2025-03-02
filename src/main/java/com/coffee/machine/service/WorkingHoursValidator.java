package com.coffee.machine.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
public class WorkingHoursValidator {

    private static final String HOLIDAY_API_URL = "https://date.nager.at/Api/v2/PublicHolidays/%d/%s";
    private static final String COUNTRY_CODE = "KZ"; // Казахстан, поменяйте при необходимости

    private final RestTemplate restTemplate;

    public WorkingHoursValidator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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

    private record Holiday(String date) {}
}
