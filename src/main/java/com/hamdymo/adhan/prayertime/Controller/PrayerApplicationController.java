package com.hamdymo.adhan.prayertime.Controller;

import com.hamdymo.adhan.prayertime.Cron.CronCreator;
import com.hamdymo.adhan.prayertime.Cron.PrayerCron;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PrayerApplicationController {
    public static final String CRONTAB_TXT = "Crontab.txt";
    private FileFacade fileFacade;
    private AdhanFacade adhanFacade;
    private CronCreator cronCreator;
    private PrayerCron prayerCron;

    public void run() throws Exception {
        //First thing we do is delete and create a new Crontab file.
        fileFacade.deleteFile(CRONTAB_TXT);
        fileFacade.createFile(CRONTAB_TXT);

        //Next we want to build all the cronjobs
        List<String> crons = prayerCron.prayersCronCreatorTest();

        //Now we want to add each cronjob
        for (String cron : crons) {
            cronCreator.addCronJobToContabFile(cron);
        }

        cronCreator.addRerunCronjob();
        cronCreator.createCrontabFromFile();




    }
}
