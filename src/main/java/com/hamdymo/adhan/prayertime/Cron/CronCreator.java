package com.hamdymo.adhan.prayertime.Cron;

public class CronCreator {

    public String createCronJobString(String time, String[] command) {
        String minute = time.substring(0,1);
        String hour = time.substring(3);
        System.out.println(minute);
        System.out.println(hour);
        return null;
    }
}
