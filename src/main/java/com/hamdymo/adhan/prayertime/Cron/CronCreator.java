package com.hamdymo.adhan.prayertime.Cron;

import java.io.IOException;

public class CronCreator {

    public String createCronJobString(String time, String[] command) {
        String minute = time.substring(0,2);
        String hour = time.substring(3);
        return null;
    }

    public String createFajrCronString(String time) {
        String minute = time.substring(0,2);
        String hour = time.substring(3);
        String fajrDirectory = getAthanDirectory(true);
        return String.format("""
                %s %s * * * play %s
                """, minute, hour, fajrDirectory);
    }

    public String getAthanDirectory(boolean isFajr) {
        String root = System.getProperty("user.dir");
        String os = System.getProperty("os.name");
        if (os.equals("Mac OS X")) {
            root = "/home/modev/workspace/prayertime";
        }
        root = root + "/assets/athaan";
        if (isFajr) {
            root = root + "/fajr.mp3";
        } else {
            root = root +"/athaan.mp3";
        }
        return root;
    }

    public void removeAllCronJobs() throws Exception {
        //first we remove all jobs from crontab
        String[] remove = {"crontab","-r"};
        Runtime.getRuntime().exec(remove);
    }

//    public void addCronJob(String cronJob) throws Exception {
//        //first we remove all jobs from crontab
//        Runtime.getRuntime().exec(remove);
//    }

    public String createTestCronString(String time) {
        String export = "export XDG_RUNTIME_DIR=\"/run/user/1000\" &&";
        String minute = time.substring(0,2);
        String hour = time.substring(3);
        String fajrDirectory = getAthanDirectory(true);
        return String.format("""
                * * * * * %s /usr/bin/play %s
                """, export,fajrDirectory);
    }
}
