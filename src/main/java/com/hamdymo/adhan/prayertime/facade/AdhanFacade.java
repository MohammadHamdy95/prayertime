package com.hamdymo.adhan.prayertime.facade;

import com.google.gson.Gson;
import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import lombok.AllArgsConstructor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;

@AllArgsConstructor
public class AdhanFacade {

    public static final String DATA = "data";
    public static final String TIMINGS = "timings";
    private OkHttpClient okHttpClient;
    private Gson gson;

    @Cacheable(value = "getPrayerTimes")
    public DailyPrayerSchedule getPrayerTimes(String date, String city) throws Exception {
        Request request = buildRequest(date, city);
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        assert response.body() != null;

        String resBody = response.body().string();
        JSONObject json = new JSONObject(resBody);
        JSONObject data = json.getJSONObject(DATA);
        JSONObject timings = data.getJSONObject(TIMINGS);
        return gson.fromJson(String.valueOf(timings), DailyPrayerSchedule.class);
    }


    private Request buildRequest(String date, String city) {
        return new Request.Builder()
                .url(buildUrl(date, city))
                .get()
                .build();
    }

    private String buildUrl(String date, String city) {
        return String.format("http://api.aladhan.com/v1/timingsByCity/%s?city=%s&country=US",
                date, city);
    }
}
