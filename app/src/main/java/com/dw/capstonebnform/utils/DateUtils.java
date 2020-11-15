package com.dw.capstonebnform.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

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
    public static final Long SIX_MONTHS_BEFORE_CURRENTDATE = new Long(6);


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
    /**
     * @param time in milliseconds (Timestamp)
     * @param mDateFormat SimpleDateFormat
     */
    public static String getDateTimeFromTimeStamp(Long time, String mDateFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(mDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateTime = new Date(time);
        return dateFormat.format(dateTime);
    }
    /**
     * Get Timestamp from date and time
     *
     * @param mDateTime datetime String
     * @param mDateFormat Date Format
     * @throws ParseException
     */
    public static long getTimeStampFromDateTime(String mDateTime, String mDateFormat)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(mDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(mDateTime);
        return date.getTime();
    }
    /**
     * Return  datetime String from date object
     *
     * @param mDateFormat format of date
     * @param date date object that you want to parse
     */
    public static String formatDateTimeFromDate(String mDateFormat, Date date) {
        if (date == null) {
            return null;
        }
        return DateFormat.format(mDateFormat, date).toString();
    }
    /**
     * Convert one date format string  to another date format string in android
     *
     * @param inputDateFormat Input SimpleDateFormat
     * @param outputDateFormat Output SimpleDateFormat
     * @param inputDate input Date String
     * @throws ParseException
     */
    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat,
                                                  String inputDate) throws ParseException {
        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat =
                new SimpleDateFormat(inputDateFormat, java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat =
                new SimpleDateFormat(outputDateFormat, java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(inputDate);
        mOutputDateString = mOutputDateFormat.format(mParsedDate);
        return mOutputDateString;
    }
}
