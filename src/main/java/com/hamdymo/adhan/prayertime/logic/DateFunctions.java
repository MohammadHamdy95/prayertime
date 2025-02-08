package com.hamdymo.adhan.prayertime.logic;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

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
}
