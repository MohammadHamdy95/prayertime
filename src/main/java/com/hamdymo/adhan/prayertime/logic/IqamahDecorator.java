package com.hamdymo.adhan.prayertime.logic;

import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.domain.model.IqamahOffset;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IqamahDecorator {
    private DateFunctions dateFunctions;
    private FileFacade fileFacade;

    public DailyPrayerSchedule addIqamahTimesToSchedule(DailyPrayerSchedule dailyPrayerSchedule) {
        IqamahOffset iqamahConfig = getIqamahConfig();
        dailyPrayerSchedule.setFajrTimeIqamah(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getFajrTime(), iqamahConfig.getFajrIqamahOffset()));
        dailyPrayerSchedule.setDhurTimeIqamah(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getDhurTime(), iqamahConfig.getDhurIqamahOffset()));
        dailyPrayerSchedule.setAsrTimeIqamah(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getAsrTime(), iqamahConfig.getAsrTimeIqamahOffset()));
        dailyPrayerSchedule.setMaghribTimeIqamah(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getMaghribTime(), iqamahConfig.getMaghribIqamahOffset()));
        dailyPrayerSchedule.setIshaTimeIqamah(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getIshaTime(), iqamahConfig.getIshaIqamahOffset()));
        return dailyPrayerSchedule;
    }

    private IqamahOffset getIqamahConfig() {
        return fileFacade.getIqamahOffsetConfig();
    }

}
