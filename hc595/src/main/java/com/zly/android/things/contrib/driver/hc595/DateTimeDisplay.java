package com.zly.android.things.contrib.driver.hc595;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class DateTimeDisplay extends NumericDisplay {
    private Timer mTimer;

    public DateTimeDisplay(String pioData, String pioSClock, String pioRClock) {
        super(pioData, pioSClock, pioRClock);
        mTimer = new Timer(true);
    }

    public void displayCurrentDate() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayDate(Clock.systemDefaultZone().millis());
            }
        }, DateTimeUtil.getStartOfDay(LocalDate.now()), DateTimeUtil.ONE_DAY);
    }

    public void displayDate(long milli) {
        display(DateTimeUtil.getDate(milli));
    }

    public void displayCurrentTime() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayTime(System.currentTimeMillis());
            }
        }, DateTimeUtil.getStratOfMinute(LocalDateTime.now()), DateTimeUtil.ONE_MINUTE);
    }

    public void displayTime(long milli) {
        display(DateTimeUtil.getTime(milli));
    }

    @Override
    public void close() throws IOException {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.close();
    }
}
