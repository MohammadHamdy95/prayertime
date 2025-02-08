package com.hamdymo.adhan.prayertime.logic;

import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class IqamahDecorator {
    private DateFunctions dateFunctions;

    public DailyPrayerSchedule addIqamahTimesToSchedule(DailyPrayerSchedule dailyPrayerSchedule) {
        return null;
    }


    private Map<String, Integer> getIqamahMap() {
        Map<String, Integer> iqamahMap = new HashMap<>();
        return iqamahMap;
    }
}
