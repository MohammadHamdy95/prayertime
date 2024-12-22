package com.hamdymo.adhan.prayertime.Config;

import com.google.gson.Gson;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
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
}
