package com.hamdymo.adhan.prayertime;

import com.hamdymo.adhan.prayertime.Controller.PrayerApplicationController;
import com.hamdymo.adhan.prayertime.domain.model.IqamahOffset;
import com.hamdymo.adhan.prayertime.logic.IqamahDecorator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PrayerTimeApplication {

    public static final String FILE_NAME = "Crontab.txt";

    public static void main(String[] args) throws Exception {
        ApplicationContext apc = SpringApplication.run(PrayerTimeApplication.class);
        PrayerApplicationController prayerApplicationController = apc.getBean(PrayerApplicationController.class);
        IqamahDecorator iqamahDecorator = apc.getBean(IqamahDecorator.class);
//        prayerApplicationController.run();
        iqamahDecorator.addIqamahTimesToSchedule(null);
    }

}
