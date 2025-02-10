package com.hamdymo.adhan.prayertime.domain.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailConfig {

    @SerializedName("send email")
    private String sendEmail;

    @SerializedName("send email password")
    private String sendEmailPassword;
}
