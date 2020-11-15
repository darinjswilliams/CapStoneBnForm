package com.dw.capstonebnform;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtilTest {

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
     public DateTimeFormatter mSimpleDateFormat = null;
     public DateTimeFormatter mSimpleTimeFormat = null;
     public static final Long SIX_MONTHS_BEFORE_CURRENTDATE = new Long(6);

    @Before
    public void setUp() throws Exception {
        mSimpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        mSimpleTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    }

    @Test
    public void testLocateDate_format3_checkNotNull() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

        Date currentDate = new Date();

        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertNotNull(localDate);
    }

    @Test
    public void testlocalDate_format3_forValidFormatOfYYYMMDD_ISNOTSAME() {
        DateTimeFormatter expectedDateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        Date currentDate = new Date();

        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertNotNull(localDate);
        Assert.assertNotSame(mSimpleDateFormat, DATE_FORMAT_3.format(localDate));
    }

    @Test
    public void testlocalDate_format3_forValidFormatOfYYYMMDD_ISSAME() {
        DateTimeFormatter expectedDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Date currentDate = new Date();

        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertNotNull(localDate);
        Assert.assertEquals(expectedDateFormat.format(localDate), DATE_FORMAT_3.format(localDate));
    }

    @Test
    public void testLocalDate_format3_forMinus6Months_WithFormatOfYYYYMMDD() {
        Date currentDate = new Date();

        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.minusMonths(SIX_MONTHS_BEFORE_CURRENTDATE);
        Assert.assertEquals(mSimpleDateFormat.format(localDate), DATE_FORMAT_3.format(localDate));
    }

    @Test
    public void testGetCurrentDate_CurrentDate_ReturnsCurrentDateWith_FormatOfYYYYMMDD() {
        Date currentDate = new Date();

        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertEquals(mSimpleDateFormat.format(localDate), DATE_FORMAT_3.format(localDate));
    }

    @Test
    public void testGetCurrentTime_DateFormat1_TimeIsFormat_WithHHmmaa() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();

        Assert.assertNotEquals(mSimpleTimeFormat.format(currentTime), DATE_FORMAT_1.format(currentTime));
    }
}
