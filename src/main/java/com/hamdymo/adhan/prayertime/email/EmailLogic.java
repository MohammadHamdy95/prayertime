package com.hamdymo.adhan.prayertime.email;

import com.hamdymo.adhan.prayertime.domain.model.DailyPrayerSchedule;
import com.hamdymo.adhan.prayertime.domain.model.User;
import com.hamdymo.adhan.prayertime.facade.AdhanFacade;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import com.hamdymo.adhan.prayertime.logic.DateFunctions;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public class EmailLogic {

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
                    .subject("test")
                    .build();
            emailSender.sendWithAttachments(sendEmailContext);
        }
    }

    private String buildBody(User user) throws Exception {
        DailyPrayerSchedule dailyPrayerSchedule = adhanFacade.getPrayerTimes(dateFunctions.getTodaysDate(), user.getCity());
        return dailyPrayerSchedule.toString();
    }
}
