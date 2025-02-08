package com.hamdymo.adhan.prayertime.domain.model;

import com.google.gson.annotations.SerializedName;

public class IqamahOffset {
    @SerializedName(value = "Fajr Iqamah Offset")
    private String fajrIqamahOffset;

    @SerializedName(value = "Dhuhr Iqamah Offset")
    private String dhurIqamahOffet;

    @SerializedName(value = "Asr Iqamah Offset")
    private String asrTimeIqamahOffset;

    @SerializedName(value = "Maghrib Iqamah Offset")
    private String maghribIqamahOffset;

    @SerializedName(value = "Isha Iqamah Offset")
    private String ishaIqamahOffset;
}
