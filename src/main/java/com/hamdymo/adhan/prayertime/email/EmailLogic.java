package com.hamdymo.adhan.prayertime.email;

import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.domain.model.User;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import com.hamdymo.adhan.prayertime.logic.DateFunctions;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class EmailLogic {

    public static final String DAILY_PRAYER_SCHEDULE = "Daily Prayer Schedule";
    private FileFacade fileFacade;
    private AdhanFacade adhanFacade;
    private DateFunctions dateFunctions;
    private EmailSender emailSender;

    public void sendUsersEmails() throws Exception {
        Map<String, User> users = fileFacade.getUsers();
        for (Map.Entry<String, User> userEntry : users.entrySet()) {
            User value = userEntry.getValue();
            SendEmailContext sendEmailContext = SendEmailContext.builder()
                    .user(value)
                    .body(buildBody(value))
                    .subject(DAILY_PRAYER_SCHEDULE)
                    .build();
            emailSender.sendWithAttachments(sendEmailContext);
        }
    }

    private String buildBody(User user) throws Exception {
        String todaysDate = dateFunctions.getDateFriendly();
        DailyPrayerSchedule dailyPrayerSchedule = adhanFacade.getPrayerTimes(dateFunctions.getTodaysDateMMddYYYY(), user.getCity());
        return String.format("""
                Hi there %s,
                Here are the prayer times for the city of %s for %s:
                
                Fajr: %s
                
                Dhuhr: %s
                
                Asr: %s
                
                Maghrib: %s
                
                Isha: %s
                
                Salams
                -Mail Robot
                """, user.getName(), todaysDate,user.getCity(), dailyPrayerSchedule.getFajrTime(), dailyPrayerSchedule.getDhurTime(),
                dailyPrayerSchedule.getAsrTime(), dailyPrayerSchedule.getMaghribTime(), dailyPrayerSchedule.getIshaTime());
    }
}
