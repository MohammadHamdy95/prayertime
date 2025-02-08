package com.hamdymo.adhan.prayertime.Cron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CronCreator {

    public String createCronJobString(String hour, String minute, String command) {
        return String.format("%s %s * * * %s", minute, hour, command);
    }

    public String createCronToRunProgramOnceAt1159am() throws IOException {
        String gradleCommand =  String.format("%s run",getGradleLocation());
        String cronJob = createCronJobString("11", "59", gradleCommand);
        System.out.println(cronJob);
        return cronJob;
    }

    public String createFajrCronString(String time) {
        String minute = time.substring(0,2);
        String hour = time.substring(3);
        String fajrDirectory = getAthanDirectory(true);
        return String.format("""
                %s %s * * * play %s
                """, minute, hour, fajrDirectory);
    }

    public String createStandardPrayerCronString(String hour, String minute) {
        String athanDirectory = getAthanDirectory(false);
        System.out.println();
        return null;
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

    public String getProjectPatch() {
        String root = System.getProperty("user.dir");
        String os = System.getProperty("os.name");
        if (os.equals("Mac OS X")) {
            root = "/home/modev/workspace/prayertime";
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
        String minute = time.substring(0,2);
        String hour = time.substring(3);
        String fajrDirectory = getAthanDirectory(true);
        return String.format("""
                * * * * * %s /usr/bin/play %s
                """, getExportCommand(),fajrDirectory);
    }

    private String getExportCommand() {
        return "export XDG_RUNTIME_DIR=\"/run/user/1000\" &&";
    }

    public void addCronJobToCronTab(String cronString) throws IOException {
        String commands = "(crontab -l ; echo \"1 2 3 4 5 /root/bin/backup.sh\") | sort - | uniq - | crontab -";
        String test = "(crontab -l ; echo \"1 2 3 4 5 /root/bin/backup.sh\") | sort - | uniq - | crontab -";
        System.out.printf("we are rnning %s", test);
//        Runtime.getRuntime().exec(test);
    }

    public String getGradleLocation() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"which","gradle"};
        Process proc = rt.exec(commands);
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        return stdInput.readLine();
    }
}
