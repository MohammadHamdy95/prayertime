package com.hamdymo.adhan.prayertime.Controller;

import com.hamdymo.adhan.prayertime.Cron.CronCreator;
import com.hamdymo.adhan.prayertime.Cron.PrayerCron;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.util.List;

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

        //Next we want to build all the cronjobs
        List<String> adhanCrons = prayerCron.prayersCronCreator();

        //Now we want to add each cronjob
        for (String cron : adhanCrons) {
            cronCreator.addCronJobToContabFile(cron);
        }

        cronCreator.addRerunCronjob();
        cronCreator.createCrontabFromFile();
        fileFacade.deleteFile(CRONTAB_TXT);
    }
}
