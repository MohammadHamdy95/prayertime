package com.hamdymo.adhan.prayertime.domain.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class IqamahOffset {
    @SerializedName(value = "Fajr Iqamah Offset")
    private int fajrIqamahOffset;

    @SerializedName(value = "Dhuhr Iqamah Offset")
    private int dhurIqamahOffset;

    @SerializedName(value = "Asr Iqamah Offset")
    private int asrTimeIqamahOffset;

    @SerializedName(value = "Maghrib Iqamah Offset")
    private int maghribIqamahOffset;

    @SerializedName(value = "Isha Iqamah Offset")
    private int ishaIqamahOffset;
}
