package com.hamdymo.adhan.prayertime.Cron;

import com.hamdymo.adhan.prayertime.domain.model.CronSchedule;
import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.domain.model.IqamahOffset;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import com.hamdymo.adhan.prayertime.logic.DateFunctions;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.ListUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class PrayerCron {
    private CronCreator cronCreator;
    private AdhanFacade adhanFacade;
    private FileFacade fileFacade;
    private DateFunctions dateFunctions;

    public List<String> athanCronCreator(DailyPrayerSchedule prayerTimes) throws Exception {
        return Arrays.asList(
                createPrayerCron(prayerTimes.getFajrTime(), true),
                createPrayerCron(prayerTimes.getDhurTime(), false),
                createPrayerCron(prayerTimes.getAsrTime(), false),
                createPrayerCron(prayerTimes.getMaghribTime(), false),
                createPrayerCron(prayerTimes.getIshaTime(), false));
    }

    public CronSchedule totalCronCreator() throws Exception {
        String city = fileFacade.getConfigCity();
        String date = dateFunctions.getTodaysDate();
        DailyPrayerSchedule dailyPrayerSchedule = adhanFacade.getPrayerTimes(date, city);
        System.out.println(dailyPrayerSchedule);
        List<String> athanCrons = athanCronCreator(dailyPrayerSchedule);
        List<String> iqamahCrons = iqamahCronCreator(dailyPrayerSchedule);
        return createCrons(athanCrons, iqamahCrons);
    }

    private CronSchedule createCrons(List<String> athanCrons, List<String> iqamahCrons) {
        return CronSchedule.builder()
                .fajrAdhan(athanCrons.get(0))
                .fajrIqamah(iqamahCrons.get(0))
                .dhuhrAdhan(athanCrons.get(1))
                .dhuhrIqamah(iqamahCrons.get(1))
                .asrAdhan(athanCrons.get(2))
                .asrIqamah(iqamahCrons.get(2))
                .maghribAdhan(athanCrons.get(3))
                .maghribIqamah(iqamahCrons.get(3))
                .ishaAdhan(athanCrons.get(4))
                .ishaIqamah(iqamahCrons.get(4))
                .build();
    }

    public List<String> iqamahCronCreator(DailyPrayerSchedule dailyPrayerSchedule) throws Exception {
        IqamahOffset iqamahOffset = fileFacade.getIqamahOffsetConfig();
        return Arrays.asList(
                createIqamahCron(dailyPrayerSchedule.getFajrTime(), iqamahOffset.getFajrIqamahOffset()),
                createIqamahCron(dailyPrayerSchedule.getDhurTime(), iqamahOffset.getDhurIqamahOffset()),
                createIqamahCron(dailyPrayerSchedule.getAsrTime(), iqamahOffset.getAsrTimeIqamahOffset()),
                createIqamahCron(dailyPrayerSchedule.getMaghribTime(), iqamahOffset.getMaghribIqamahOffset()),
                createIqamahCron(dailyPrayerSchedule.getIshaTime(), iqamahOffset.getMaghribIqamahOffset()));
    }

    private String createPrayerCron(String time, boolean isFajr) throws IOException {
        String hour = time.substring(0, 2);
        String minute = time.substring(3);
        String timings = createTimings(minute, hour);
        String exportCommand = getExportCommand();
        String playLocation = getPlayLocation();
        String athanDirectory = getAthanDirectory(isFajr);
        return String.format("""
                %s %s && %s %s
                """, timings, exportCommand, playLocation, athanDirectory);
    }

    private String createIqamahCron(String time, int offset) throws IOException {
        time = dateFunctions.addMinutesToHourMinuteString(time, offset);
        String hour = time.substring(0, 2);
        String minute = time.substring(3);
        String timings = createTimings(minute, hour);
        String exportCommand = getExportCommand();
        String playLocation = getPlayLocation();
        String athanDirectory = getIqamahDirectory();
        return String.format("""
                %s %s && %s %s
                """, timings, exportCommand, playLocation, athanDirectory);
    }

    private String getExportCommand() {
        return "export XDG_RUNTIME_DIR=\"/run/user/1000\"";
    }

    private String createTimings(String minute, String hour) {
        return String.format("%s %s * * *", minute, hour);
    }

    private String getAthanDirectory(boolean isFajr) {
        String root = System.getProperty("user.dir");
        root = root + "/assets/athaan";
        if (isFajr) {
            root = root + "/fajr.wav";
        } else {
            root = root + "/athaan.wav";
        }
        return root;
    }

    private String getIqamahDirectory() {
        String root = System.getProperty("user.dir");
        root = root + "/assets/athaan";
        root = root + "/iqamah.wav";
        return root;
    }

    private String getPlayLocation() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"which", "play"};
        Process proc = rt.exec(commands);
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        return stdInput.readLine();
    }
}
