package com.zly.android.things.contrib.driver.hc595;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {
    public static final long ONE_DAY = 24 * 60 * 60 * 1000;
    public static final long ONE_MINUTE = 60 * 1000;

    /**
     * @param milli time
     * @return "MM.dd"，11.11
     */
    public static String getDate(long milli) {
        return DateTimeFormatter
                .ofPattern("MM.dd")
                .withZone(ZoneId.systemDefault())
                .format(Instant.ofEpochMilli(milli));
    }

    /**
     * @param milli time
     * @return "HH.mm"， 08.16
     */
    public static String getTime(long milli) {
        return DateTimeFormatter
                .ofPattern("mm.ss")
                .withZone(ZoneId.systemDefault())
                .format(Instant.ofEpochMilli(milli));
    }

    public static Date getStartOfDay(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getStratOfMinute(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
