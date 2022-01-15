package com.sbrf.reboot.lesson5;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Lesson {
    public final DayOfWeek weekDay;
    public final LocalTime beginTime;
    public final LocalTime endTime;
    public final String name;

    public Lesson(DayOfWeek weekDay, LocalTime beginTime, LocalTime endTime, String name) {
        this.weekDay = weekDay;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("[%s - %s] %s", beginTime, endTime, name);
    }
}
