package com.hamdymo.adhan.prayertime.Controller;

import com.hamdymo.adhan.prayertime.Cron.CronCreator;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;

import java.io.IOException;

public class PrayerApplicationController {
    public static final String CRONTAB_TXT = "Crontab.txt";
    private FileFacade fileFacade;
    private AdhanFacade adhanFacade;
    private CronCreator cronCreator;

    public void run() throws IOException {
        //First thing we do is delete and create a new Crontab file.
        fileFacade.deleteFile(CRONTAB_TXT);
        fileFacade.createFile(CRONTAB_TXT);

        //Next we want to build all the cronjobs




    }
}
