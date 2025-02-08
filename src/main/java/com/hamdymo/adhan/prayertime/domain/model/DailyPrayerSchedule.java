package com.hamdymo.adhan.prayertime.domain.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyPrayerSchedule {

    /**
     * Looks like "05:57";
     */
    @SerializedName(value = "Fajr")
    private String fajrTime;

    @SerializedName(value = "Dhuhr")
    private String dhurTime;

    @SerializedName(value = "Asr")
    private String asrTime;

    @SerializedName(value = "Maghrib")
    private String maghribTime;

    @SerializedName(value = "Isha")
    private String ishaTime;

    private String fajrTimeIqamah;

    private String dhurTimeIqamah;

    private String asrTimeIqamah;

    private String maghribTimeIqamah;

    private String ishaTimeIqamah;
}
