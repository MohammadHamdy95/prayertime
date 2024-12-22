package com.hamdymo.adhan.prayertime;

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
		String date = "12-22-2024";
		String city = "Bellevue";
		DailyPrayerSchedule dailyPrayerSchedule = adhanFacade.getPrayerTimes(date, city);
		System.out.println(dailyPrayerSchedule);
	}

}
