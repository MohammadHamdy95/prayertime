package com.hamdymo.adhan.prayertime.domain.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Config {
    private String city;

    @SerializedName("zip code")
    private String zipCode;

    @SerializedName("timezone id")
    private String timezoneId;

    private String email;
}
