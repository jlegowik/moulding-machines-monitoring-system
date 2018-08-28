package com.ogel.common.date;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * This class helps to set UTC timezone by default
 */
public class DateUtils {

    public static OffsetDateTime fromMills(Long millis) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
    }

    public static String getStringDate(OffsetDateTime dateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneOffset.UTC)
                .format(dateTime);
    }

    public static Timestamp toTimestamp(OffsetDateTime date) {
        return Timestamp.from(date.toInstant());
    }

    public static OffsetDateTime from(Instant instant) {
        return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    public static OffsetDateTime fromTimestamp(Timestamp date) {
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
    }


    public static Timestamp toTimestamp(Instant date) {
        return Timestamp.from(OffsetDateTime.ofInstant(date, ZoneOffset.UTC).toInstant());
    }
}
