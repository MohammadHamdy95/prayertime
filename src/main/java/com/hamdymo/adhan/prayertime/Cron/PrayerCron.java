package com.hamdymo.adhan.prayertime.Cron;

import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import com.hamdymo.adhan.prayertime.logic.DateFunctions;
import lombok.AllArgsConstructor;

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

    public List<String> prayersCronCreator() throws Exception {
        String city = fileFacade.getConfigCity();
        String date = dateFunctions.getDateTomorrow();
        DailyPrayerSchedule prayerTimes = adhanFacade.getPrayerTimes(date, city);
        return Arrays.asList(
                createPrayerCron(prayerTimes.getFajrTime(), true),
                createPrayerCron(prayerTimes.getDhurTime(), false),
                createPrayerCron(prayerTimes.getAsrTime(), false),
                createPrayerCron(prayerTimes.getMaghribTime(), false),
                createPrayerCron(prayerTimes.getIshaTime(), false));
    }

    public List<String> IqamahCronCreator() throws Exception {
        String city = fileFacade.getConfigCity();
        String date = dateFunctions.getDateTomorrow();
        DailyPrayerSchedule prayerTimes = adhanFacade.getPrayerTimes(date, city);
        return Arrays.asList(
                createPrayerCron(prayerTimes.getFajrTime(), true),
                createPrayerCron(prayerTimes.getDhurTime(), false),
                createPrayerCron(prayerTimes.getAsrTime(), false),
                createPrayerCron(prayerTimes.getMaghribTime(), false),
                createPrayerCron(prayerTimes.getIshaTime(), false));
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

    private String createIqamahCron(String time, boolean isFajr) throws IOException {
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
            root = root + "/fajr.mp3";
        } else {
            root = root + "/athaan.mp3";
        }
        return root;
    }

    private String getIqamahDirectory(boolean isFajr) {
        String root = System.getProperty("user.dir");
        root = root + "/assets/athaan";
        root = root + "/athaan.mp3";
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
