package com.hamdymo.adhan.prayertime.controller;

import com.hamdymo.adhan.prayertime.cron.CronCreator;
import com.hamdymo.adhan.prayertime.cron.PrayerCron;
import com.hamdymo.adhan.prayertime.domain.model.CronSchedule;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

/**
 * This is the controller layer of PrayerApplicationController.
 */
@AllArgsConstructor
public class PrayerApplicationController {
    private static final String CRONTAB_TXT = "Crontab.txt";
    private FileFacade fileFacade;
    private CronCreator cronCreator;
    private PrayerCron prayerCron;

    public void run() throws Exception {
        //First thing we do is delete and create a new Crontab file.
        fileFacade.deleteFile(CRONTAB_TXT);
        fileFacade.createFile(CRONTAB_TXT);
        cronCreator.addRerunCronjob();

        //Next we want to build all the cronjobs
        CronSchedule adhanCrons = prayerCron.totalCronCreator();

        cronCreator.addLinesToCronTabFile(adhanCrons);

        cronCreator.createCrontabFromFile();
        fileFacade.deleteFile(CRONTAB_TXT);
    }
}
