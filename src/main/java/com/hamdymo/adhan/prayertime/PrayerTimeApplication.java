package com.hamdymo.adhan.prayertime;

import com.hamdymo.adhan.prayertime.Controller.PrayerApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
public class PrayerTimeApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext apc = SpringApplication.run(PrayerTimeApplication.class);
        PrayerApplicationController prayerApplicationController = apc.getBean(PrayerApplicationController.class);
        prayerApplicationController.run();
    }

}
