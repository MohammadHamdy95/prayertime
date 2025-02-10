package com.hamdymo.adhan.prayertime.email;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class EmailSender {

    public void sendWithAttachments(SendEmailContext sendEmailContext) throws IOException, MessagingException {

        try {
            String recipientEmail = IntegTestVariables.SEND_EMAIL
                    ? sendEmailContext.getCfSystemsTableEntry().getEmail()
                    : EmailConstants.MY_EMAIL;
            MimeMessage msg = new MimeMessage(getQualifiedSession(EmailConstants.MY_EMAIL, EmailConstants.MY_EMAIL_PASSWORD));
            msg.setFrom(EmailConstants.MY_EMAIL);

            msg.setRecipients(Message.RecipientType.TO,
                    recipientEmail);
            msg.setSubject(sendEmailContext.getSubject());
            msg.setSentDate(new Date());

            // create the message body part
            Multipart multipart = getMultipart(sendEmailContext);

            // set the content of the email to the Multipart object
            msg.setContent(multipart);

            // send the email message
            Transport.send(msg);
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }

        log.info("Email sent to : {}", "mo");
    }
}
