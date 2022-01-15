package com.sbrf.reboot.lesson5;

import com.sbrf.reboot.lesson5.templates.NotificationTemplate;

import java.time.LocalDateTime;

public class Notification {
    public final LocalDateTime dateTime;
    public final String text;

    private Notification(LocalDateTime dateTime, String text) {
        this.dateTime = dateTime;
        this.text = text;
    }

    public static Notification closest(NotificationTemplate template) {
        return new Notification(template.closestNotification(), template.generateMessage());
    }
}
