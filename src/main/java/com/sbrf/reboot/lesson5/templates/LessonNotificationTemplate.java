package com.sbrf.reboot.lesson5.templates;

import com.sbrf.reboot.lesson5.Lesson;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class LessonNotificationTemplate extends NotificationTemplate {
    private final Lesson lesson;

    private LessonNotificationTemplate(DayOfWeek weekDay, LocalTime timeAtDay, Lesson lesson) {
        this.weekDay = weekDay;
        this.timeAtDay = timeAtDay;
        this.lesson = lesson;
    }

    /**
     * @param beforeTime offset in minutes (one day (1440 min) is maximum)
     * @param lesson {@link Lesson}
     */
    public static LessonNotificationTemplate of(int beforeTime, Lesson lesson) {
        return new LessonNotificationTemplate(
                lesson.beginTime.getMinute() - beforeTime < 0 ? lesson.weekDay.minus(1) : lesson.weekDay,
                lesson.beginTime.minusMinutes(beforeTime),
                lesson
        );
    }

    @Override
    public String generateMessage() {
        return lesson.toString();
    }
}
