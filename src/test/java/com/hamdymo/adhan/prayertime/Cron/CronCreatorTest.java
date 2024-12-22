package com.hamdymo.adhan.prayertime.Cron;


import org.junit.jupiter.api.Test;

public class CronCreatorTest {

    //Class to test
    CronCreator cronCreator = new CronCreator();

    @Test
    public void testicle5() {
        String time = "06:14";
        cronCreator.createCronJobString(time, null);
    }
}