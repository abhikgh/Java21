package com.src.stream;


import com.src.model.Items;

//java.time(Java 8) replaces java.util.Date

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class DateEx {

    //java.util.Date class was deprecated since introduction of java.time API in Java 8
    //java.sql.Date is just java.util.Date with its time set to 00:00:00 but the point in design perspective is that java.sql.* is not meant for a front layer which clients interact with l

    public static void main(String[] args) throws ParseException, DatatypeConfigurationException {

        /*
            Date, LocalDateTime and Timestamp do not have any Zone information

                ZoneId :: Time-Zone such as "Europe/Paris"
                ex: ZoneId zone = ZoneId.of("Europe/Berlin");

                ZoneOffset :: extends ZoneId and defines the fixed offset of the current time-zone w.r.t GMT/UTC, such as +02:00.

            ZonedDateTime ::  ZonedDateTime = LocalDateTime + ZoneId + ZoneOffSet
                ex: 2007-12-03T10:15:30+01:00 Europe/Paris

                ZonedDateTime.now(zoneId);
                ZonedDateTime.of(localDateTime, zoneId);

                ZonedDateTime withZoneSameInstant :: convert zonedDateTime from one TimeZone to another TimeZone
                ZonedDateTime zonedDateTimeZ2 = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Berlin"));

            OffsetDateTime :: LocalDateTime + ZoneOffSet-UTC
                OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.of("+02:00"));

            ZonedDateTime  vs OffsetDateTime
                Store OffsetDateTime in the database over the ZonedDateTime
                problem with OffsetDateTime is does not match with any real time zone

            Date ::  Date = LocalDateTime + ZoneId

            Instant :: ZonedDateTime + ZoneId(UTC)

            DateTimeFormatter.ofPattern
        */

        // Today's date
        LocalDate today = LocalDate.now();
        System.out.println(today); // 2021-08-04

        // Current date-time in format
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        System.out.println(currentDateTime); //2021-08-04T15:27:28.180

        //String to LocalDate
        String input = "1990-12-12";
        DateTimeFormatter dateTimeFormatter11 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(input, dateTimeFormatter11);
        System.out.println(localDate); //1990-12-12

        // +- Date
        LocalDate oneMonthAfter = LocalDate.now().plusMonths(1);
        System.out.println(oneMonthAfter); // 2021-09-04
        LocalDate oneDayAfter = LocalDate.now().plusDays(1);
        System.out.println(oneDayAfter); //2021-09-05
        LocalTime twoHoursBefore = LocalTime.now().minusHours(2);
        System.out.println(twoHoursBefore); // 13:29:26.584741300

        // Check if two dates are equal
        LocalDate localDate2 = LocalDate.of(2026, 12, 26);
        LocalDate localDate3 = LocalDate.of(2021, 12, 26);
        String isEqual = localDate2.equals(localDate3) ? "equal" : "notEqual";
        System.out.println(isEqual); // notEqual

        //Months between
        long monthsBetween = ChronoUnit.MONTHS.between(localDate3.withDayOfMonth(1), localDate2.withDayOfMonth(1));
        System.out.println("monthsBetween :: " + monthsBetween); //60

        monthsBetween = ChronoUnit.MONTHS.between(localDate3, localDate2);
        System.out.println("monthsBetween :: " + monthsBetween); //60

        //Days between
        long daysBetween = ChronoUnit.DAYS.between(localDate3.withDayOfMonth(1), localDate2.withDayOfMonth(1));
        System.out.println(-daysBetween); //-1826

        //currentTime
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(currentTime);    //11:34:12

        //isBefore
        String[] currArr = currentTime.split(":");
        LocalTime localTime1 = LocalTime.of(Integer.parseInt(currArr[0]), Integer.parseInt(currArr[1]), Integer.parseInt(currArr[2]));
        LocalTime localTime2 = LocalTime.of(16, 45, 00);
        System.out.println(localTime1.isBefore(localTime2)); //true

        //days between 2 SimpleDateFormat
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        long date1SDF = simpleDateFormat.parse("2017-09-18 12:34:34.009").getTime();
        long date2SDF = simpleDateFormat.parse("2019-09-18 12:35:34.009").getTime();
        long days = TimeUnit.DAYS.convert(Math.abs(date1SDF - date2SDF), TimeUnit.MILLISECONDS);
        System.out.println(days); //730

        //ZoneOffset , ZonedDateTime
        //In case a country has 2 different offsets – in summer and winter, there will be 2 different ZoneOffset implementations for the same region, hence the need to specify a LocalDateTime.
        ZoneOffset zoneOffSet = ZoneId.of("Europe/Berlin").getRules().getOffset(LocalDateTime.now());
        System.out.println("zoneOffSet :: " + zoneOffSet);

        //Current ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        System.out.println("zonedDateTime ::" + zonedDateTime); //2024-07-06T11:43:03.404982300+02:00[Europe/Berlin]

        ZonedDateTime zonedDateTime2 = Instant.now().atZone(ZoneId.of("Europe/Berlin"));
        System.out.println("zonedDateTime2 ::" + zonedDateTime2); //2024-07-06T11:43:03.406984200+02:00[Europe/Berlin]

        zonedDateTime = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println("zonedDateTime :: " + zonedDateTime);
        Date dateNow = Date.from(zonedDateTime.toInstant());
        System.out.println("dateNow :: " + dateNow);
       
        zonedDateTime = Instant.now().atZone(ZoneId.of("Europe/Berlin"));
        System.out.println("zonedDateTime ::" + zonedDateTime); //:2024-07-06T11:40:36.984765300+02:00[Europe/Berlin]

        //util.Date to sql.Date
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("utilDate:" + utilDate);
        System.out.println("sqlDate:" + sqlDate);

        //next day ZonedDateTime
        ZonedDateTime nextDayZonedDateTime = ZonedDateTime.now(ZoneId.of("US/Pacific")).plusDays(1).with(LocalTime.MIDNIGHT);
        System.out.println("nextDayZonedDateTime :: " + nextDayZonedDateTime);

        //String to ZonedDateTime
        String inputDateTime = "2023-08-31T23:49:59Z";
        ZonedDateTime zonedDateTimeZ = ZonedDateTime.of(LocalDateTime.parse(inputDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")), ZoneId.of("America/New_York"));
        System.out.println("zonedDateTimeZ :: " + zonedDateTimeZ);

        //String to ZonedDateTime (UTC)
        inputDateTime = "2023-08-31T23:49:59Z";
        ZonedDateTime zonedDateTimeUTC = ZonedDateTime.of(LocalDateTime.parse(inputDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")), ZoneId.of("UTC"));
        System.out.println("zonedDateTimeUTC :: " + zonedDateTimeUTC);

        boolean isSameDateZ = zonedDateTimeZ.toLocalDate().isEqual(zonedDateTimeUTC.toLocalDate());
        System.out.println("isSameDateZ :: " + isSameDateZ);

        //Convert from 1 ZonedDateTime to another ZonedDateTime
        ZonedDateTime zonedDateTimeZ2 = ZonedDateTime.now(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Europe/Berlin"));
        System.out.println("zonedDateTimeZ2 :: " + zonedDateTimeZ2);

        //ZonedDateTime to LocalDateTime
        LocalDateTime toLocalDateTime = ZonedDateTime.now(ZoneId.of("US/Pacific")).toLocalDateTime();
        System.out.println("toLocalDateTime :: " + toLocalDateTime);

        //Format ZonedDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        String formattedZonedDateTime = dateTimeFormatter.format(zonedDateTime);
        System.out.println("formattedZonedDateTime :: " + formattedZonedDateTime); //2023-09-07T11:07:50.975 -0700

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        formattedZonedDateTime = dateTimeFormatter.format(zonedDateTime);
        System.out.println("formattedZonedDateTime :: " + formattedZonedDateTime); //2023-09-07T11:07:50.975-07:00

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formattedZonedDateTime = dateTimeFormatter.format(zonedDateTime);
        System.out.println("formattedZonedDateTime :: " + formattedZonedDateTime); //2023-09-07T11:09:18.056Z

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS a"); // AM/PM
        formattedZonedDateTime = dateTimeFormatter.format(zonedDateTime);
        System.out.println("formattedZonedDateTime :: " + formattedZonedDateTime); //2023-09-07T11:10:00.137 am

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.S");
        formattedZonedDateTime = dateTimeFormatter.format(zonedDateTime);
        System.out.println("formattedZonedDateTime :: " + formattedZonedDateTime); //2023-09-07T11:11:29.0

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.n");
        formattedZonedDateTime = dateTimeFormatter.format(zonedDateTime);
        System.out.println("formattedZonedDateTime :: " + formattedZonedDateTime); //2023-09-07T11:31:26.425469300

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedZonedDateTime = dateTimeFormatter.format(zonedDateTime);
        System.out.println("formattedZonedDateTime :: " + formattedZonedDateTime); //2023-09-07 11:12:11

        //String to date
        input = "2023-03-22";
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat2.parse(input);
        System.out.println("date :: " + date);

        //String to LocalDate
        input = "1990-12-12";
        localDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("localDate :: " + localDate); //1990-12-12

        //String to LocalDateTime
        inputDateTime = "2023-06-02T00:00:00Z";
        LocalDateTime localDateDT = LocalDateTime.parse(inputDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        System.out.println("localDateDT = " + localDateDT); // 2023-06-02T00:00

        //LocalDateTime to String
        String localDateDTStr = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        System.out.println("localDateDTStr = " + localDateDTStr); //2023-09-07

        //get current UTC datetime as LocalDateTime
        LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneId.of("UTC"));
        System.out.println("currentUTCDateTime :: " + currentUTCDateTime);

        //next day UTC date time
        LocalDateTime nextDaytUTCDateTime = LocalDateTime.now(ZoneId.of("UTC")).plusDays(1).with(LocalTime.MIDNIGHT);
        System.out.println(nextDaytUTCDateTime);

        //java.sql.Timestamp to LocalDateTime and to String
        Timestamp timestamp = new Timestamp(2333L);
        LocalDateTime ldt = timestamp.toLocalDateTime();
        System.out.println("ldt :: " + ldt);
        String formattedLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(ldt);
        System.out.println("formattedLocalDateTime :: " + formattedLocalDateTime);

        //convert java.sql.Timestamp in a ZoneId to LocalDateTime UTC
        //gets an Instant as milliseconds passed from the epoch of 1970-01-01T00:00:00Z
        String publishedTime = "7628961292073";
        LocalDateTime localDateTime0 = Instant.ofEpochMilli(Long.parseLong(publishedTime)).atZone(ZoneId.of("Europe/Berlin")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        formattedLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(localDateTime0);
        System.out.println("formattedLocalDateTime2:: " + formattedLocalDateTime); //2211-10-03T03:54:52Z

        //hoursDiffNextDay
        var hoursDiffNextDay = ChronoUnit.HOURS.between(currentUTCDateTime, nextDaytUTCDateTime);
        System.out.println("hoursDiffNextDay " + hoursDiffNextDay);

        //String to LocalDateTime
        LocalDateTime localDateTime1 = LocalDateTime.parse("2023-08-31T23:59:59Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        System.out.println("localDateTime1 :: " + localDateTime1);
        LocalDateTime localDateTime2 = LocalDateTime.parse("2023-06-13T23:59:59Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        System.out.println("localDateTime2 :: " + localDateTime2);

        //LocalDateTime to ZonedDateTime
        zonedDateTime = LocalDateTime.now().atZone(ZoneId.of("UTC"));

        //LocalDateTime to ZonedDateTime to Date in UTC
        zonedDateTime = LocalDateTime.now().atZone(ZoneId.of("UTC"));
        Date dateNowUTC = Date.from(zonedDateTime.toInstant());
        System.out.println("dateNowUTC 000 :: " + dateNowUTC);

        //String in TZ Asia/Kolkata to time in UTC
        input =   "06-12-2015 02:10:10 am";
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss a");
        LocalDateTime localDateTime = LocalDateTime.parse(input, dateTimeFormatter);
        zonedDateTimeUTC = localDateTime.atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC"));
        dateNowUTC = Date.from(zonedDateTimeUTC.toInstant());
        System.out.println("dateNowUTC from Asia/Kolkata :: " + dateNowUTC);

        //compare 2 LocalDateTime for dates
        boolean isSameDate = localDateTime1.toLocalDate().isEqual(localDateTime2.toLocalDate());
        System.out.println("isSameDate :: " + isSameDate);

        Items item1 = new Items("2339282", localDateTime1, "ART");
        Items item2 = new Items("9393929", localDateTime2, "SGR");
        List<Items> itemsList = new ArrayList<>();
        itemsList.addAll(Arrays.asList(item1, item2));

        var hoursDiffSet = itemsList.stream().map(item -> ChronoUnit.HOURS.between(currentUTCDateTime, item.getValidTo())).collect(Collectors.toSet());
        System.out.println("hoursDiffSet :: " + hoursDiffSet);
        var hourDiffSetMin = hoursDiffSet.stream().min(Long::compare).get();
        System.out.println(hourDiffSetMin);

        final var cacheExpiry = LongStream.of(hoursDiffNextDay, hourDiffSetMin).min().getAsLong();
        Duration duration = Duration.of(cacheExpiry, ChronoUnit.HOURS);
        System.out.println(duration);

        /*
            private LocalDateTime validTo;

            private ZonedDateTime getTimeAtZone(ZonedDateTime inputDateTime, String businessUnitTimezone) {
                return inputDateTime.withZoneSameInstant(ZoneId.of(businessUnitTimezone));
            }
           ChronoUnit.MINUTES.between(businessUnitLocalTime, getTimeAtZone(validTo.atZone(ZoneId.of(UTC))
                   
            if (minimumPriceValidityOfFees == 0L) {
                return Duration.of(minimumPriceValidityOfSalesPrice, ChronoUnit.MINUTES);
            } else {
                return Duration.of(Math.min(minimumPriceValidityOfSalesPrice, minimumPriceValidityOfFees), ChronoUnit.MINUTES);
            }
        */

        //LocalDateTime to XMLGregorianCalendar
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDateTime.now().toString());
        System.out.println(xmlGregorianCalendar); //2021-08-04T13:17:57.417918600

        //LocalDateTime to XMLGregorianCalendar in specific format
        ZonedDateTime zonedDateTime1 = xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime();
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        XMLGregorianCalendar xmlGregorianCalendar2 = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(dateTimeFormatter1.format(zonedDateTime1));
        System.out.println(xmlGregorianCalendar2); //2021-08-04T13:26:01

        //Compare dates in XMLGregorianCalendar
        XMLGregorianCalendar xmlGregorianCalendar1 = DatatypeFactory.newInstance().newXMLGregorianCalendar("2021-05-28");
        XMLGregorianCalendar currentDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.now().toString());
        System.out.println(xmlGregorianCalendar1.toGregorianCalendar().getTime().before(currentDate.toGregorianCalendar().getTime())); //true

        // XMLGregorianCalendar to LocalDateTime
        XMLGregorianCalendar xmlGregorianCalendar3 = DatatypeFactory.newInstance().newXMLGregorianCalendar("2021-02-03T10:21:36.000Z");
        localDateTime = xmlGregorianCalendar3.toGregorianCalendar()
                .toZonedDateTime()
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
        System.out.println(localDateTime); //2021-02-03T15:51:36

        // XMLGregorianCalendar to XMLGregorianCalendar
        XMLGregorianCalendar xmlGregorianCalendar11 = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("2019-02-03T10:21:36.000Z");
        ZonedDateTime zonedDateTime11 = xmlGregorianCalendar11.toGregorianCalendar().toZonedDateTime();
        DateTimeFormatter dateTimeFormatter21 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        XMLGregorianCalendar xmlGregorianCalendar21 = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(dateTimeFormatter21.format(zonedDateTime11));
        System.out.println(xmlGregorianCalendar21); //2019-02-03T10:21:36

        //XMLGregorianCalendar to LocalDate
        XMLGregorianCalendar xmlGregorianCalendarLD = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(LocalDateTime.now().toString());
        localDate2 = LocalDate.of(xmlGregorianCalendarLD.getYear(), xmlGregorianCalendarLD.getMonth(), xmlGregorianCalendarLD.getDay());
        System.out.println(localDate2);  //2021-08-04

        // XMLGregorianCalendar to String
        XMLGregorianCalendar xmlGregorianCalendar12 = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("2019-02-03T10:21:36.000Z");
        ZonedDateTime zonedDateTime12 = xmlGregorianCalendar12.toGregorianCalendar().toZonedDateTime();
        DateTimeFormatter dateTimeFormatter12 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date12 = dateTimeFormatter12.format(zonedDateTime12);
        System.out.println(date12); //2019-02-03

        //XMLGregorianCalendar-Current Date : Date difference
        XMLGregorianCalendar xmlGregorianCalendarDF = DatatypeFactory.newInstance().newXMLGregorianCalendar("2019-02-03T10:21:36.000Z");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateOld = simpleDateFormat.format(xmlGregorianCalendarDF.toGregorianCalendar().getTime());
        long t1 = simpleDateFormat.parse(dateOld).getTime();
        String todayStr = simpleDateFormat.format(new Date());
        long t2 = simpleDateFormat.parse(todayStr).getTime();
        long daysDiff = TimeUnit.DAYS.convert(Math.abs(t1 - t2), TimeUnit.MILLISECONDS);
        System.out.println(daysDiff); //912

        //Current date as XMLGregorian
        XMLGregorianCalendar xmlGregorianCalendarC = DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.now().toString());
        ZonedDateTime zonedDateTimeC = xmlGregorianCalendarC.toGregorianCalendar().toZonedDateTime();
        DateTimeFormatter dateTimeFormatterC = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        XMLGregorianCalendar xmlGregorianCalendarCO = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeFormatterC.format(zonedDateTimeC));
        System.out.println(xmlGregorianCalendarCO);


        // XMLGregorianCalendar to XMLGregorianCalendar
        XMLGregorianCalendar xmlGregorianCalendarF = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("2019-02-03Z");
        ZonedDateTime zonedDateTimeF = xmlGregorianCalendarF.toGregorianCalendar().toZonedDateTime();
        DateTimeFormatter dateTimeFormatterF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        XMLGregorianCalendar xmlGregorianCalendarF2 = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(dateTimeFormatterF.format(zonedDateTimeF));
        System.out.println(xmlGregorianCalendarF2); //2019-02-03


        //Change date format
        SimpleDateFormat inputFormat00 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat00 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        System.out.println(outputFormat00.format(inputFormat00.parse("2021-07-26 10:35:16"))); //2021-07-26T10:35:16.000+05:30

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(outputFormat.format(inputFormat.parse("2016-02-03T16:23:18"))); //2016-02-03 16:23:18.000

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        System.out.println(outputFormatter.format(LocalDate.parse("2018-04-10T04:00:00.000Z", inputFormatter))); //10-04-2018

        DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("dd-MM-yyy");
        System.out.println(outputFormatter2.format(LocalDate.parse("2018-11-05T00:00:00.000", inputFormatter2))); //05-11-2018

        DateTimeFormatter inputFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        DateTimeFormatter outputFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(outputFormatter3.format(LocalDateTime.parse("2018-11-05T12:23:14.789", inputFormatter3))); //2018-11-05 12:23:14.789

        //Check Date setLenient= false
        String inputDate = "2001Nov05";

        Pattern pattern = Pattern.compile("^\\d{4}\\w{3}\\d{2}$");
        Matcher matcher = pattern.matcher(inputDate);
        if (matcher.matches()) {
            simpleDateFormat = new SimpleDateFormat("yyyyMMMdd");
            simpleDateFormat.setLenient(false);
            try {
                Date date20 = simpleDateFormat.parse(inputDate);
                System.out.println(date20);  //Mon Nov 05 00:00:00 IST 2001
            } catch (Exception e) {
                System.out.println("Invalid date :: date");
            }
        } else {
            System.out.println("Invalid date :: format");
        }

        inputDate = "2012-03-20T11:22:33Z";
        pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$");
        matcher = pattern.matcher(inputDate);
        if (matcher.matches()) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            simpleDateFormat.setLenient(false);
            try {
                Date date20 = simpleDateFormat.parse(inputDate);
                System.out.println(date20);  //Tue Mar 20 11:22:33 IST 2012
            } catch (Exception e) {
                System.out.println("Invalid date :: date");
            }
        } else {
            System.out.println("Invalid date :: format");
        }

        //OffsetDateTime
        OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.of("+02:00"));
        System.out.println("offSetDateTime: " + offsetDateTime); // 2023-09-21T05:41:54.748054400+02:00

        //get Day_Of_Week from a date
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 12);
        calendar.set(Calendar.MONTH, 0); //January :: 0
        calendar.set(Calendar.YEAR, 2023);
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        System.out.println("dayOfWeek :: " + dayOfWeek); //Thursday

    }

}
