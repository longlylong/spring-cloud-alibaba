package com.thatday.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Slf4j
public class EmailUtil {

    private static final String spring_mail_host = "smtp.exmail.qq.com";
    private static final String spring_mail_protocol = "smtp";
    private static final String spring_mail_port = "465";

    private static final String spring_mail_username = "spring_mail_username";
    private static final String spring_mail_password = "spring_mail_password";

    private static void sendEmail(String fromAddress, String personal, List<String> toList, String subject, String content) {
        try {
            Session session = getSession();
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(fromAddress, personal));

            for (String to : toList) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            }

            mimeMessage.setSubject(subject);
            mimeMessage.setSentDate(new Date());

            mimeMessage.setText(content);
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);

        } catch (Exception i) {
            log.info("---发送邮件报错----");
        }
    }

    private static Session getSession() throws GeneralSecurityException {
        Properties properties = new Properties();

        properties.put("mail.transport.protocol", spring_mail_protocol);
        properties.put("mail.smtp.host", spring_mail_host);
        properties.put("mail.smtp.port", spring_mail_port);
        properties.put("mail.smtp.auth", true);
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(spring_mail_username, spring_mail_password);
            }
        });
//            session.setDebug(true);
        return session;
    }

}
