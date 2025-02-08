package com.hamdymo.adhan.prayertime;

import com.hamdymo.adhan.prayertime.Controller.PrayerApplicationController;
import com.hamdymo.adhan.prayertime.Cron.CronCreator;
import com.hamdymo.adhan.prayertime.Cron.PrayerCron;
import com.hamdymo.adhan.prayertime.domain.model.Config;
import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import com.hamdymo.adhan.prayertime.logic.DateFunctions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PrayerTimeApplication {

	public static final String FILE_NAME = "Crontab.txt";

	public static void main(String[] args) throws Exception {
		ApplicationContext apc = SpringApplication.run(PrayerTimeApplication.class);
		PrayerApplicationController prayerApplicationController = apc.getBean(PrayerApplicationController.class);
		prayerApplicationController.run();
	}

}
