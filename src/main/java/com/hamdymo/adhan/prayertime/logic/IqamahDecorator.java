package com.hamdymo.adhan.prayertime.logic;

import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.domain.model.IqamahOffset;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class IqamahDecorator {
    private DateFunctions dateFunctions;
    private FileFacade fileFacade;

    public DailyPrayerSchedule addIqamahTimesToSchedule(DailyPrayerSchedule dailyPrayerSchedule) {
        IqamahOffset iqamahConfig = getIqamahConfig();
        return null;
    }

    private IqamahOffset getIqamahConfig() {
        return fileFacade.getIqamahOffsetConfiig();
    }

}
