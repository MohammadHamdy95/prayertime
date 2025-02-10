package com.hamdymo.adhan.prayertime.logic;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@AllArgsConstructor
public class DateFunctions {
    public static final String SAMPLE_DATE = "01-21-1995 11:18:00";
    public static final String ZERO = "0";
    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("MM-dd-yyyy HH:mm:ss");
    public static final int TEN = 10;
    private FileFacade fileFacade;

    /**
     * @return String of data looks like "22-12-2024"
     */
    public String getDateTomorrow() {
        String timezoneId = fileFacade.getConfigFile().getTimezoneId();
        DateTimeZone zone = DateTimeZone.forID(timezoneId);
        DateTime dt = new DateTime(zone).plusDays(1);
        int day = dt.getDayOfMonth();
        int month = dt.getMonthOfYear();
        int year = dt.getYear();
        return generateAPIDate(day, month, year);
    }

    public String getDateFriendly() {
        String timezoneId = fileFacade.getConfigFile().getTimezoneId();
        DateTimeZone zone = DateTimeZone.forID(timezoneId);
        DateTime dt = new DateTime(zone);
        return dt.toString();
    }

    /**
     * @return String of data looks like "22-12-2024"
     */
    public String getTodaysDateMMddYYYY() {
        String timezoneId = fileFacade.getConfigFile().getTimezoneId();
        DateTimeZone zone = DateTimeZone.forID(timezoneId);
        DateTime dt = new DateTime(zone);
        int day = dt.getDayOfMonth();
        int month = dt.getMonthOfYear();
        int year = dt.getYear();
        return generateAPIDate(day, month, year);
    }

    private String generateAPIDate(int day, int month, int year) {
        return String.format("%d-%d-%d", day, month, year);
    }

    /**
     * @param hourMinute "05:57"
     * @return hour minute shown above with a designated amount of minutes to add to it.
     */
    public String addMinutesToHourMinuteString(String hourMinute, int minutesToAdd) {
        String sampleDate = setTimeOfDayToString(SAMPLE_DATE, hourMinute);
        DateTime dateTime = FORMATTER.parseDateTime(sampleDate);
        DateTime revive = dateTime.plusMinutes(minutesToAdd);
        String betterHour = revive.getHourOfDay() < TEN ? ZERO + revive.getHourOfDay() : String.valueOf(revive.getHourOfDay());
        String betterMinute = revive.getMinuteOfHour() < TEN ? ZERO + revive.getMinuteOfHour() : String.valueOf(revive.getMinuteOfHour());

        return String.format("%s:%s", betterHour, betterMinute);
    }

    /**
     * @param date         give MM-dd-yyyy HH:mm:ss, I will add custom hours and minutes to it.
     * @param hoursMinutes give something like "05:57"
     * @return
     */
    public String setTimeOfDayToString(String date, String hoursMinutes) {
        String cleanHourMinuteSeconds = addSecondsToHours(hoursMinutes);
        return date.substring(0, 11) + cleanHourMinuteSeconds;
    }

    private String addSecondsToHours(String hoursMinutes) {
        return hoursMinutes + ":00";
    }
}
