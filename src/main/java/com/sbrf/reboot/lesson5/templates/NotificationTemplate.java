package com.sbrf.reboot.lesson5.templates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.sbrf.reboot.lesson5.ClockBean.clock;

public abstract class NotificationTemplate {
    protected DayOfWeek weekDay;
    protected LocalTime timeAtDay;

    public LocalDateTime closestNotification() {
        LocalDateTime now = LocalDateTime.now(clock());
        LocalDate date = now.toLocalDate();
        while (!date.getDayOfWeek().equals(weekDay)) {
            date = date.plusDays(1);
        }
        LocalDateTime closest = date.atTime(timeAtDay);
        if (closest.isAfter(now)) return closest;
        else return closest.plusDays(7);
    }

    public String generateMessage() {
        return null;
    }
}
