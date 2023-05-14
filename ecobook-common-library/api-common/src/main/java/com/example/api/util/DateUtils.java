package com.example.api.util;

import java.sql.Timestamp;
import java.time.Instant;

public class DateUtils {

    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp toTimestamp(Instant instant) {
        return new Timestamp(instant.toEpochMilli());
    }
}
