package com.hamdymo.adhan.prayertime.domain.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class User {
    private String name;

    private String city;

    @SerializedName("timezone id")
    private String timezoneId;

    @SerializedName("zip code")
    private String zipCode;

    private String email;
}
