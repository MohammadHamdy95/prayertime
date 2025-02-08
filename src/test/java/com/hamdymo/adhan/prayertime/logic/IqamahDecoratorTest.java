package com.hamdymo.adhan.prayertime.logic;

import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.domain.model.IqamahOffset;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IqamahDecoratorTest {

    public static final String FAJR_TIME = "05:57";
    public static final String DHUR_TIME = "12:23";
    public static final String ASR_TIME = "14:56";
    public static final String MAGHRIB_TIME = "17:23";
    public static final String ISHA_TIME = "18:50";

    public static final String FAJR_TIME_HOUR_OFFSET = "06:57";
    public static final String DHUR_TIME_HOUR_OFFSET = "13:23";
    public static final String ASR_TIME_HOUR_OFFSET = "15:56";
    public static final String MAGHRIB_TIME_HOUR_OFFSET = "18:23";
    public static final String ISHA_TIME_HOUR_OFFSET = "19:50";
    public static final int OFFSET = 60;

    @Mock
    private DateFunctions dateFunctions;

    @Mock
    private FileFacade fileFacade;

    @InjectMocks
    private IqamahDecorator iqamahDecorator;

    //Test variables
    private IqamahOffset iqamahOffset;
    private DailyPrayerSchedule dailyPrayerSchedule;

    @BeforeEach
    void setUp() {
        initIqamahOffset();
        initDailyPrayerSchedule();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addIqamahTimesToSchedule() {
        when(fileFacade.getIqamahOffsetConfiig()).thenReturn(iqamahOffset);
        when(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getFajrTime(), iqamahOffset.getFajrIqamahOffset())).thenReturn(FAJR_TIME_HOUR_OFFSET);
        when(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getDhurTime(), iqamahOffset.getDhurIqamahOffset())).thenReturn(DHUR_TIME_HOUR_OFFSET);
        when(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getAsrTime(), iqamahOffset.getAsrTimeIqamahOffset())).thenReturn(ASR_TIME_HOUR_OFFSET);
        when(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getMaghribTime(), iqamahOffset.getMaghribIqamahOffset())).thenReturn(MAGHRIB_TIME_HOUR_OFFSET);
        when(dateFunctions.addMinutesToHourMinuteString(dailyPrayerSchedule.getIshaTime(), iqamahOffset.getIshaIqamahOffset())).thenReturn(ISHA_TIME_HOUR_OFFSET);


        DailyPrayerSchedule result = iqamahDecorator.addIqamahTimesToSchedule(dailyPrayerSchedule);

        assertEquals(FAJR_TIME_HOUR_OFFSET,result.getFajrTimeIqamah());
        assertEquals(DHUR_TIME_HOUR_OFFSET,result.getDhurTimeIqamah());
        assertEquals(ASR_TIME_HOUR_OFFSET,result.getAsrTimeIqamah());
        assertEquals(MAGHRIB_TIME_HOUR_OFFSET,result.getMaghribTimeIqamah());
        assertEquals(ISHA_TIME_HOUR_OFFSET,result.getIshaTimeIqamah());

        verify(dateFunctions, times(1)).addMinutesToHourMinuteString(dailyPrayerSchedule.getFajrTime(), iqamahOffset.getFajrIqamahOffset());
        verify(dateFunctions, times(1)).addMinutesToHourMinuteString(dailyPrayerSchedule.getDhurTime(), iqamahOffset.getDhurIqamahOffset());
        verify(dateFunctions, times(1)).addMinutesToHourMinuteString(dailyPrayerSchedule.getAsrTime(), iqamahOffset.getAsrTimeIqamahOffset());
        verify(dateFunctions, times(1)).addMinutesToHourMinuteString(dailyPrayerSchedule.getMaghribTime(), iqamahOffset.getMaghribIqamahOffset());
        verify(dateFunctions, times(1)).addMinutesToHourMinuteString(dailyPrayerSchedule.getIshaTime(), iqamahOffset.getIshaIqamahOffset());

    }

    private void initIqamahOffset() {
        iqamahOffset = IqamahOffset.builder()
                .fajrIqamahOffset(OFFSET)
                .dhurIqamahOffset(OFFSET)
                .asrTimeIqamahOffset(OFFSET)
                .maghribIqamahOffset(OFFSET)
                .ishaIqamahOffset(OFFSET)
                .build();
    }

    private void initDailyPrayerSchedule() {
        dailyPrayerSchedule = DailyPrayerSchedule.builder()
                .fajrTime(FAJR_TIME)
                .dhurTime(DHUR_TIME)
                .asrTime(ASR_TIME)
                .maghribTime(MAGHRIB_TIME)
                .ishaTime(ISHA_TIME)
                .build();
    }
}