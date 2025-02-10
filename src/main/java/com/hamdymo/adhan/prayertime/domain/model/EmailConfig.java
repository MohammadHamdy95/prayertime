package com.hamdymo.adhan.prayertime.domain.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailConfig {
    private String sendEmail;
    private String sendEmailPassword;
}
