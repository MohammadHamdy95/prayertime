package com.hamdymo.adhan.prayertime;

import com.hamdymo.adhan.prayertime.Controller.PrayerApplicationController;
import com.hamdymo.adhan.prayertime.domain.model.IqamahOffset;
import com.hamdymo.adhan.prayertime.logic.DateFunctions;
import com.hamdymo.adhan.prayertime.logic.IqamahDecorator;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
public class PrayerTimeApplication {

    public static final String FILE_NAME = "Crontab.txt";

    public static void main(String[] args) throws Exception {
        ApplicationContext apc = SpringApplication.run(PrayerTimeApplication.class);
        PrayerApplicationController prayerApplicationController = apc.getBean(PrayerApplicationController.class);
        IqamahDecorator iqamahDecorator = apc.getBean(IqamahDecorator.class);
        DateFunctions dateFunctions = apc.getBean(DateFunctions.class);
//        prayerApplicationController.run();
//        iqamahDecorator.addIqamahTimesToSchedule(null);
//        dateFunctions.setTimeOfDayToString();
    }

}
