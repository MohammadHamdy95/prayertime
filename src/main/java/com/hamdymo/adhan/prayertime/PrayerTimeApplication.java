package com.hamdymo.adhan.prayertime;

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
		AdhanFacade adhanFacade = apc.getBean(AdhanFacade.class);
		CronCreator cronCreator = apc.getBean(CronCreator.class);
		FileFacade fileFacade = apc.getBean(FileFacade.class);
		DateFunctions dateFunctions = apc.getBean(DateFunctions.class);
		PrayerCron prayerCron = apc.getBean(PrayerCron.class);
//		cronCreator.createCronToRunProgramOnceAt1159am();
//		System.out.println(fileFacade.getConfigFile());
//		DailyPrayerSchedule prayerTimes = adhanFacade.getPrayerTimes("01-01-2025", "Bellevue");
//		String asrTime = prayerTimes.getAsrTime();
//		System.out.println(asrTime);
		prayerCron.fajrCron("12:12");



//		fileFacade.viewFile(FILE_NAME);
//		fileFacade.deleteFile(FILE_NAME);
//		fileFacade.createFile(FILE_NAME);
//		String date = "12-22-2024";
//		String city = "Bellevue";
//		cronCreator.removeAllCronJobs();
//		cronCreator.addCronJobToCronTab(null);
//		DailyPrayerSchedule dailyPrayerSchedule = adhanFacade.getPrayerTimes(date, city);
//		System.out.println(dailyPrayerSchedule);
//		System.out.println(cronCreator.createTestCronString(dailyPrayerSchedule.getFajrTime()));
	}

}
