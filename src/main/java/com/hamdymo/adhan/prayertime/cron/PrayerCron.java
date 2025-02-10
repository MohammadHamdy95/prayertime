package com.hamdymo.adhan.prayertime.cron;

import com.hamdymo.adhan.prayertime.domain.model.CronSchedule;
import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.domain.model.IqamahOffset;
import com.hamdymo.adhan.prayertime.domain.model.User;
import com.hamdymo.adhan.prayertime.email.EmailSender;
import com.hamdymo.adhan.prayertime.email.SendEmailContext;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import com.hamdymo.adhan.prayertime.logic.DateFunctions;
import com.hamdymo.adhan.prayertime.logic.IqamahDecorator;
import jakarta.mail.MessagingException;
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
    private EmailSender emailSender;
    private IqamahDecorator iqamahDecorator;

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
        String date = dateFunctions.getTodaysDateMMddYYYY();
        DailyPrayerSchedule dailyPrayerSchedule = adhanFacade.getPrayerTimes(date, city);
        List<String> athanCrons = athanCronCreator(dailyPrayerSchedule);
        List<String> iqamahCrons = iqamahCronCreator(dailyPrayerSchedule);
        CronSchedule crons = createCrons(athanCrons, iqamahCrons);
        sendEmail(dailyPrayerSchedule);
        return crons;
    }

    private void sendEmail(DailyPrayerSchedule dailyPrayerSchedule) throws MessagingException, IOException {
        iqamahDecorator.addIqamahTimesToSchedule(dailyPrayerSchedule);
        String email = fileFacade.getConfigFile().getEmail();
        String city = fileFacade.getConfigCity();
        String timeZoneId = fileFacade.getConfigFile().getTimezoneId();
        User user = User.builder()
                .email(email)
                .city(city)
                .name("Mohammad Hamdy")
                .timezoneId(timeZoneId)
                .build();
        SendEmailContext sendEmailContext = SendEmailContext.builder()
                .subject("Your Prayer Summary")
                .body(buildBody(dailyPrayerSchedule, user))
                .build();
        emailSender.sendWithAttachments(sendEmailContext);
        System.out.println(dailyPrayerSchedule);
    }

    private String buildBody(DailyPrayerSchedule dailyPrayerSchedule, User user) {
        String test = String.format("""
                Hi %s,
                we have completed generation of your Alarms!
                In %s for %s times are as follows:
                
                Fajr: %s
                Fajr Iqamah: %s
                
                Dhuhr: %s
                Dhuhr Iqamah: %s
                
                Asr: %s
                Asr Iqamah: %s
                
                Maghrib: %s
                Maghrib Iqamah: %s
                
                Isha: %s
                Isha Iqamah %s
                
                """,user.getName(), user.getCity(), dateFunctions.getDateFriendly(), dailyPrayerSchedule.getFajrTime(),
                dailyPrayerSchedule.getFajrTimeIqamah(), dailyPrayerSchedule.getDhurTime(), dailyPrayerSchedule.getDhurTimeIqamah(),
                dailyPrayerSchedule.getAsrTime(), dailyPrayerSchedule.getAsrTimeIqamah(), dailyPrayerSchedule.getMaghribTime(), dailyPrayerSchedule.getMaghribTimeIqamah(),
                dailyPrayerSchedule.getIshaTime(), dailyPrayerSchedule.getIshaTimeIqamah());

        return test;
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
                createIqamahCron(dailyPrayerSchedule.getIshaTime(), iqamahOffset.getIshaIqamahOffset()));
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
