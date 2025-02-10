package com.hamdymo.adhan.prayertime.config;

import com.google.gson.Gson;
import com.hamdymo.adhan.prayertime.controller.PrayerApplicationController;
import com.hamdymo.adhan.prayertime.cron.CronCreator;
import com.hamdymo.adhan.prayertime.cron.PrayerCron;
import com.hamdymo.adhan.prayertime.domain.model.EmailConfig;
import com.hamdymo.adhan.prayertime.domain.model.SecretConfig;
import com.hamdymo.adhan.prayertime.email.EmailSender;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import com.hamdymo.adhan.prayertime.logic.DateFunctions;
import com.hamdymo.adhan.prayertime.logic.IqamahDecorator;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

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
        return new PrayerApplicationController(fileFacade(), cronCreator(), prayerCron());
    }

    @Bean
    public IqamahDecorator iqamahDecorator() {
        return new IqamahDecorator(dateFunctions(), fileFacade());
    }

    @Bean
    public SecretConfig secretConfig() throws IOException {
        return fileFacade().getSecretConfig();
    }

    @Bean
    public EmailSender emailSender() throws IOException {
        return new EmailSender(secretConfig());
    }
}
