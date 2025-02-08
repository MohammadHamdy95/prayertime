package com.hamdymo.adhan.prayertime.logic;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@AllArgsConstructor
public class DateFunctions {
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

    private String generateAPIDate(int day, int month, int year) {
        return String.format("%d-%d-%d", day, month, year);
    }

    /**
     * @param hourMinute "05:57"
     * @return hour minute shown above with a designated amount of minutes to add to it.
     */
    public String addMinutesToHourMinuteString(String hourMinute, int minutesToAdd) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy HH:mm:ss");
        String sampleDate = "01-21-1995 11:18:00";
        DateTime dateTime = formatter.parseDateTime(sampleDate);
        dateTime.getMillisOfDay();
        return null;
    }

    /**
     * @param date give MM-dd-yyyy HH:mm:ss, I will add custom hours and minutes to it.
     * @param hoursMinutes give something like "05:57"
     * @return
     */
    public String setTimeOfDayToString(String date, String hoursMinutes) {
        String cleanHourMinuteSeconds = addSecondsToHours(hoursMinutes);
        String test = date.substring(0,12);
        System.out.println(test);
        return null;
    }

    private String addSecondsToHours(String hoursMinutes) {
        return hoursMinutes+":00";
    }
}
