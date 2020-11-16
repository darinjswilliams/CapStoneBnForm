package com.dw.capstonebnform.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static final DateTimeFormatter DATE_FORMAT_1 =  DateTimeFormatter.ofPattern("hh:mm a");
    public static final DateTimeFormatter DATE_FORMAT_2 = DateTimeFormatter.ofPattern("h:mm a");
    public static final DateTimeFormatter DATE_FORMAT_3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_FORMAT_4 = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
    public static final DateTimeFormatter DATE_FORMAT_5 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final DateTimeFormatter DATE_FORMAT_6 = DateTimeFormatter.ofPattern("dd MMMM yyyy zzzz");
    public static final DateTimeFormatter DATE_FORMAT_7 = DateTimeFormatter.ofPattern("EEE, MMM d, ''yy");
    public static final DateTimeFormatter DATE_FORMAT_8 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT_9 = DateTimeFormatter.ofPattern("h:mm a dd MMMM yyyy");
    public static final DateTimeFormatter DATE_FORMAT_10 = DateTimeFormatter.ofPattern("K:mm a, z");
    public static final DateTimeFormatter DATE_FORMAT_11 = DateTimeFormatter.ofPattern("hh 'o''clock' a, zzzz");
    public static final DateTimeFormatter DATE_FORMAT_12 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static final DateTimeFormatter DATE_FORMAT_13 = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss z");
    public static final DateTimeFormatter DATE_FORMAT_14 = DateTimeFormatter.ofPattern("yyyy.MM.dd G 'at' HH:mm:ss z");
    public static final DateTimeFormatter DATE_FORMAT_15 = DateTimeFormatter.ofPattern("yyyyy.MMMMM.dd GGG hh:mm a");
    public static final DateTimeFormatter DATE_FORMAT_16 = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z");
    public static final DateTimeFormatter DATE_FORMAT_17 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static final DateTimeFormatter DATE_FORMAT_18 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    public static final DateTimeFormatter DATE_FORMAT_19 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final Long SIX_MONTHS_BEFORE_CURRENTDATE = 6L;


    public static String getCurrentDateWithFormatYYYYMMDD() {
        Date currentDate = new Date();

        LocalDate today = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return DATE_FORMAT_3.format(today);
    }
    public static String getCurrentTime() {

        LocalDateTime currentTime = LocalDateTime.now();

        return DATE_FORMAT_1.format(currentTime);
    }

    public static String getSixMonthsBeforeCurrentDate(){
        Date currentDate = new Date();
        LocalDate today = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        today = today.minusMonths(SIX_MONTHS_BEFORE_CURRENTDATE);

        return DATE_FORMAT_3.format(today);
    }

}
