package com.coffee.machine.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;

public class WorkingHoursValidator {
    private static final LocalTime START_TIME = LocalTime.of(8, 0); // 08:00
    private static final LocalTime END_TIME = LocalTime.of(17, 0); // 17:00

    public static boolean isWorkingHours() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        DayOfWeek dayOfWeek = now.getDayOfWeek();

        // Проверяем, что сейчас рабочее время
        boolean isWithinWorkingHours = currentTime.isAfter(START_TIME) && currentTime.isBefore(END_TIME);

        // Проверяем, что не выходной (суббота/воскресенье)
        boolean isWeekday = dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;

        return isWithinWorkingHours && isWeekday;
    }
}
