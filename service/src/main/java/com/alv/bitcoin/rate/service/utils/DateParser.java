package com.alv.bitcoin.rate.service.utils;
/*
 * Created by alysonlv - 2019-03-01
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateParser {

    private static final String PATTERN = "yyyy-MM-ddTHH:mm:";

    private DateParser() {
    }

    public static final LocalDateTime parseToLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
    }
}
