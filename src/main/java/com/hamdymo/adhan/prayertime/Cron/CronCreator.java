package com.hamdymo.adhan.prayertime.Cron;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class CronCreator {

    private static final String CRONTAB_TXT = "Crontab.txt";
    private FileFacade fileFacade;

    public void addCronJobToContabFile(String cron) {
        fileFacade.addLineToFile(cron, CRONTAB_TXT);
    }

    public void createCronJob() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"crontab", "<", fileFacade.getFilenamePath(CRONTAB_TXT)};
        Process proc = rt.exec(commands);
    }
}
