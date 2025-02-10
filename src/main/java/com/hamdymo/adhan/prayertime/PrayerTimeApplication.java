package com.hamdymo.adhan.prayertime;

import com.hamdymo.adhan.prayertime.controller.PrayerApplicationController;
import com.hamdymo.adhan.prayertime.domain.model.User;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import java.util.Map;

@SpringBootApplication
@EnableCaching
public class PrayerTimeApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext apc = SpringApplication.run(PrayerTimeApplication.class);
        PrayerApplicationController prayerApplicationController = apc.getBean(PrayerApplicationController.class);
//        prayerApplicationController.run();
        FileFacade bean = apc.getBean(FileFacade.class);

        Map<String, User> users = bean.getUsers();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            String key = entry.getKey();
            User value = entry.getValue();
            System.out.println(key + ": " + value);
        }
    }

}
