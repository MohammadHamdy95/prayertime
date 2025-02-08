package com.hamdymo.adhan.prayertime.Config;

import com.google.gson.Gson;
import com.hamdymo.adhan.prayertime.Controller.PrayerApplicationController;
import com.hamdymo.adhan.prayertime.Cron.CronCreator;
import com.hamdymo.adhan.prayertime.Cron.PrayerCron;
import com.hamdymo.adhan.prayertime.PrayerTimeApplication;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import com.hamdymo.adhan.prayertime.logic.DateFunctions;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Here, all the beans of greatness are configured and injected!
 */
@Configuration
public class BeanConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder().build();
    }

    @Bean
    public AdhanFacade adhanFacade() {
        return new AdhanFacade(okHttpClient(), gson());
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public CronCreator cronCreator() {
        return new CronCreator(fileFacade());
    }

    @Bean
    public FileFacade fileFacade() {
        return new FileFacade(gson());
    }

    @Bean
    public DateFunctions dateFunctions() {
        return new DateFunctions(fileFacade());
    }

    @Bean
    public PrayerCron prayerCron() {
        return new PrayerCron(cronCreator(), adhanFacade(), fileFacade(), dateFunctions());
    }

    @Bean
    public PrayerApplicationController prayerApplicationController() {
        return new PrayerApplicationController(fileFacade(), adhanFacade(), cronCreator(), prayerCron());
    }
}
