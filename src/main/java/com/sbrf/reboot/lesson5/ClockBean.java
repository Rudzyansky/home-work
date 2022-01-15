package com.sbrf.reboot.lesson5;

import java.time.Clock;

public class ClockBean {
    public static Clock clock() {
        return Clock.systemDefaultZone();
    }
}
