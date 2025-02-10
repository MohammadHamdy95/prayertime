package com.hamdymo.adhan.prayertime.domain.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class User {
    private String name;

    @SerializedName("zip code")
    private String zipCode;

    @SerializedName("timezone id")
    private String timezoneId;
}
