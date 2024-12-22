package com.hamdymo.adhan.prayertime.facade;

import com.google.gson.Gson;
import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import lombok.AllArgsConstructor;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

@AllArgsConstructor
public class AdhanFacade {

    public static final String LATITUDE_ATTR = "latitude";
    public static final String LONGITUDE_ATTR = "longitude";
    public static final String DATA = "data";
    public static final String TIMINGS = "timings";
    public static final String CONTENT_TYPE = "text/plain";
    private OkHttpClient okHttpClient;
    private Gson gson;

    public void getPrayerMap(String date, String latitude, String longitude) throws Exception {
        Request request = buildRequest(date, latitude, longitude);
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        assert response.body() != null;

        String resBody = response.body().string();
        JSONObject json = new JSONObject(resBody);
        JSONObject jsonObject = json.getJSONObject(DATA).getJSONObject(TIMINGS);
        DailyPrayerSchedule dailyPrayerSchedule = gson.fromJson(String.valueOf(jsonObject), DailyPrayerSchedule.class);
        System.out.println(dailyPrayerSchedule);
    }


    private Request buildRequest(String date, String latitude, String longitude) {
        MediaType mediaType = MediaType.parse(CONTENT_TYPE);
        Request request = new Request.Builder()
                .url(buildUrl(date, latitude, longitude))
                .get()
                .build();
        System.out.println(request);
        return request;
    }

    private String buildUrl(String date, String latitude, String longitude) {
        return String.format("http://api.aladhan.com/v1/timings/%s?%s=%s&%s=%s",
                date, LATITUDE_ATTR, latitude, LONGITUDE_ATTR, longitude);
    }
}
