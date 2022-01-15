package com.sbrf.reboot.lesson5.templates;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TextNotificationTemplate extends NotificationTemplate {
    private final String text;

    private TextNotificationTemplate(DayOfWeek weekDay, LocalTime timeAtDay, String text) {
        this.weekDay = weekDay;
        this.timeAtDay = timeAtDay;
        this.text = text;
    }

    public static TextNotificationTemplate of(DayOfWeek weekDay, LocalTime timeAtDay, String text) {
        return new TextNotificationTemplate(weekDay, timeAtDay, text);
    }

    @Override
    public String generateMessage() {
        return text;
    }
}
