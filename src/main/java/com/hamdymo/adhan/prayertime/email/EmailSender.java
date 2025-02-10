package com.hamdymo.adhan.prayertime.email;

import com.hamdymo.adhan.prayertime.domain.model.EmailConfig;
import com.hamdymo.adhan.prayertime.domain.model.SecretConfig;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Slf4j
@AllArgsConstructor
public class EmailSender {

    private SecretConfig secretConfig;

    public void sendWithAttachments(SendEmailContext sendEmailContext) throws IOException, MessagingException {

        EmailConfig emailConfig = secretConfig.getEmailConfig();
        try {
            String recipientEmail = sendEmailContext.getUser().getEmail();
            MimeMessage msg = new MimeMessage(getQualifiedSession(emailConfig.getSendEmail(), emailConfig.getSendEmailPassword()));
            msg.setFrom(emailConfig.getSendEmail());

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

    private Session getQualifiedSession(String email, String password) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }

    private static Multipart getMultipart(SendEmailContext sendEmailContext) throws MessagingException, IOException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(sendEmailContext.getBody());

        // create a second MimeBodyPart to hold the attachment
        MimeBodyPart attachmentPart = new MimeBodyPart();
        String directory = sendEmailContext.getFileDirectory(); // Specify the file path
        attachmentPart.attachFile(new File(directory));

        // create a Multipart object to combine the message body and the attachment
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);  // Add message body
        multipart.addBodyPart(attachmentPart);   // Add attachment
        return multipart;
    }
}
