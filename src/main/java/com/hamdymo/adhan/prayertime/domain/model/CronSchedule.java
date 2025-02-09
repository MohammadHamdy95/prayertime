package com.hamdymo.adhan.prayertime.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CronSchedule {
    private String fajrAdhan;
    private String fajrIqamah;
    private String dhuhrAdhan;
    private String dhuhrIqamah;
    private String asrAdhan;
    private String asrIqamah;
    private String maghribAdhan;
    private String maghribIqamah;
    private String ishaAdhan;
    private String ishaIqamah;
}
