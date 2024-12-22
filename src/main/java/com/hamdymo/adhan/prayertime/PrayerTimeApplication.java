package com.hamdymo.adhan.prayertime;

import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PrayerTimeApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext apc = SpringApplication.run(PrayerTimeApplication.class);
		AdhanFacade adhanFacade = apc.getBean(AdhanFacade.class);
		String date = "21-01-2025";
		String lat = "47.610378";
		String longitude = "-122.200676";
		adhanFacade.getPrayerMap(date, lat, longitude);

	}

}
