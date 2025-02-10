package com.hamdymo.adhan.prayertime.email;

import com.hamdymo.adhan.prayertime.domain.model.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SendEmailContext {
    private String body;
    private String subject;
    private User user;
    private String fileDirectory;
}
