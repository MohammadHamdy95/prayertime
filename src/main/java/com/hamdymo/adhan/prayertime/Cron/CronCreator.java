package com.hamdymo.adhan.prayertime.Cron;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@AllArgsConstructor
public class CronCreator {

    private FileFacade fileFacade;

    public void addCronJob(String cron) {
        fileFacade.addLineToFile(cron, "Cronjob");
    }
}
