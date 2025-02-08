package com.hamdymo.adhan.prayertime.Cron;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@AllArgsConstructor
public class CronCreator {

    private static final String CRONTAB_TXT = "Crontab.txt";
    private FileFacade fileFacade;

    public void addCronJobToContabFile(String cron) {
        fileFacade.addLineToFile(cron, CRONTAB_TXT);
    }

    public void createCronjobFileToCronjob() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"crontab", "<", fileFacade.getFilenamePath(CRONTAB_TXT)};
        String[] commands2 = {"crontab", "-e"};
        Process proc = rt.exec(commands2);
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        System.out.println(stdInput.readLine());
        System.out.printf("%s %s %s",commands[0],commands[1],commands[2]);
    }

    public void addRerunCronjob() throws IOException {
        String test = String.format("""
                57 11 * * * cd %s && %s run
                """, getProjectDirectory(), getGradleLocation());
        addCronJobToContabFile(test);
    }
    private String getProjectDirectory() {
        String root = System.getProperty("user.dir");
        return root;
    }

    private String getGradleLocation() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"which","gradle"};
        Process proc = rt.exec(commands);
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        return stdInput.readLine();
    }
}
