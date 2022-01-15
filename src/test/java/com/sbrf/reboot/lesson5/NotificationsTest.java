package com.sbrf.reboot.lesson5;

import com.sbrf.reboot.lesson5.templates.DayNotificationTemplate;
import com.sbrf.reboot.lesson5.templates.LessonNotificationTemplate;
import com.sbrf.reboot.lesson5.templates.NotificationTemplate;
import com.sbrf.reboot.lesson5.templates.TextNotificationTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class NotificationsTest {
    final ArrayList<Lesson> lessons = new ArrayList<>();
    final ArrayList<NotificationTemplate> notificationTemplates = new ArrayList<>();

    @BeforeEach
    void setup() {
        lessons.add(new Lesson(
                DayOfWeek.WEDNESDAY,
                LocalTime.of(18, 30),
                LocalTime.of(20, 0),
                "Java Development"
        ));
        lessons.add(new Lesson(
                DayOfWeek.WEDNESDAY,
                LocalTime.of(20, 10),
                LocalTime.of(21, 30),
                "Java Development"
        ));
        lessons.add(new Lesson(
                DayOfWeek.FRIDAY,
                LocalTime.of(18, 30),
                LocalTime.of(20, 0),
                "Java Development"
        ));
        lessons.add(new Lesson(
                DayOfWeek.FRIDAY,
                LocalTime.of(20, 10),
                LocalTime.of(21, 30),
                "Java Development"
        ));

        // Notification Templates
        for (DayOfWeek weekDay : DayOfWeek.values()) {
            List<Lesson> day = lessons.stream()
                    .filter(l -> weekDay.equals(l.weekDay))
                    .collect(Collectors.toList());
            if (day.size() > 0) {
                notificationTemplates.add(DayNotificationTemplate.of(weekDay, 3 * 60, day));
            }
            day.stream()
                    .map(l -> LessonNotificationTemplate.of(5, l))
                    .forEach(notificationTemplates::add);
        }
        notificationTemplates.add(TextNotificationTemplate.of(DayOfWeek.WEDNESDAY, LocalTime.MIN, "It is Wednesday, my dudes"));
    }

    @Test
    void generateNotifications() {
        String expected = "[2022-01-18T21:00]\n" +
                "{\n" +
                "Wednesday\n" +
                "\n" +
                "[18:30 - 20:00] Java Development\n" +
                "[20:10 - 21:30] Java Development\n" +
                "}\n" +
                "\n" +
                "[2022-01-19T00:00]\n" +
                "{\n" +
                "It is Wednesday, my dudes\n" +
                "}\n" +
                "\n" +
                "[2022-01-19T18:25]\n" +
                "{\n" +
                "[18:30 - 20:00] Java Development\n" +
                "}\n" +
                "\n" +
                "[2022-01-19T20:05]\n" +
                "{\n" +
                "[20:10 - 21:30] Java Development\n" +
                "}\n" +
                "\n" +
                "[2022-01-20T21:00]\n" +
                "{\n" +
                "Friday\n" +
                "\n" +
                "[18:30 - 20:00] Java Development\n" +
                "[20:10 - 21:30] Java Development\n" +
                "}\n" +
                "\n" +
                "[2022-01-21T18:25]\n" +
                "{\n" +
                "[18:30 - 20:00] Java Development\n" +
                "}\n" +
                "\n" +
                "[2022-01-21T20:05]\n" +
                "{\n" +
                "[20:10 - 21:30] Java Development\n" +
                "}";

        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime dateTime = LocalDateTime.of(2022, 1, 10, 0, 0);
        Clock clock = Clock.fixed(dateTime.atZone(zone).toInstant(), zone);

        try (MockedStatic<ClockBean> mockedStatic = mockStatic(ClockBean.class)) {
            mockedStatic.when(ClockBean::clock).thenReturn(clock);
        }

        List<Notification> notifications = notificationTemplates.stream()
                .map(Notification::closest)
                .sorted(Comparator.comparing(n -> n.dateTime))
                .collect(Collectors.toList());
        String actual = notifications.stream()
                .map(n -> "[" + n.dateTime + "]\n{\n" + n.text + "\n}")
                .collect(Collectors.joining("\n\n"));

        assertEquals(expected, actual);
    }
}
