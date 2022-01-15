package com.sbrf.reboot.lesson5.templates;

import com.sbrf.reboot.lesson5.Lesson;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class DayNotificationTemplate extends NotificationTemplate {
    private final List<Lesson> lessons;

    private DayNotificationTemplate(DayOfWeek weekDay, LocalTime timeAtDay, List<Lesson> lessons) {
        this.weekDay = weekDay;
        this.timeAtDay = timeAtDay;
        this.lessons = lessons;
    }

    /**
     * @param weekDay {@link DayOfWeek}
     * @param beforeTime offset by day begin (00:00) in minutes (one day (1440 min) is maximum)
     * @param lessons list of {@link Lesson}
     */
    public static DayNotificationTemplate of(DayOfWeek weekDay, int beforeTime, List<Lesson> lessons) {
        return new DayNotificationTemplate(
                beforeTime > 0 ? weekDay.minus(1) : weekDay,
                LocalTime.MIN.minusMinutes(beforeTime),
                lessons
        );
    }

    @Override
    public String generateMessage() {
        DayOfWeek weekDay = lessons.stream().findFirst().orElseThrow(NoSuchElementException::new).weekDay;
        String listOfLessons = lessons.stream().map(Lesson::toString).collect(Collectors.joining("\n"));
        return String.format("%s\n\n%s", weekDay.getDisplayName(TextStyle.FULL, Locale.ENGLISH), listOfLessons);
    }
}
