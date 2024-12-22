package com.hamdymo.adhan.prayertime.Cron;


import org.junit.jupiter.api.Test;

public class CronCreatorTest {

    //Class to test
    CronCreator cronCreator = new CronCreator();

    @Test
    public void test_cronjob() {
        String time = "06:14";
        String[] command = {"",""};
        cronCreator.createCronJobString(time, null);
    }
}