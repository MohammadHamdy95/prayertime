package com.hamdymo.adhan.prayertime;

import com.hamdymo.adhan.prayertime.Cron.CronCreator;
import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PrayerTimeApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext apc = SpringApplication.run(PrayerTimeApplication.class);
		AdhanFacade adhanFacade = apc.getBean(AdhanFacade.class);
		CronCreator cronCreator = apc.getBean(CronCreator.class);
		String date = "12-22-2024";
		String city = "Bellevue";
		cronCreator.removeAllCronJobs();
//		DailyPrayerSchedule dailyPrayerSchedule = adhanFacade.getPrayerTimes(date, city);
//		System.out.println(dailyPrayerSchedule);
//		System.out.println(cronCreator.createTestCronString(dailyPrayerSchedule.getFajrTime()));
	}

}
