package com.thatday.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class EmailUtil {

    private static final String spring_mail_host = "smtp.exmail.qq.com";
    private static final String spring_mail_protocol = "smtp";
    private static final String spring_mail_username = "huangjinlong@gdzol.com.cn";
    private static final String spring_mail_password = "Zk888999";
    private static final String spring_mail_port = "465";

    private static final String ToEmail4 = "huangjinlong@gdzol.com.cn";
    private static final String ToEmail3 = "hu.yanlong@gdzol.com.cn";
    private static final String ToEmail2 = "yang.long@gdzol.com.cn";
    private static final String ToEmail1 = "13510256695@163.com";


    public static void sendOrderEmail(String buyerName, String buyerPhone, String orderTime, String orderNum,
                                      Double orderPrice, String goodsNames) {
        try {
            Session session = getSession();
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(spring_mail_username, "SaaS小秘书"));

            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(ToEmail1));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(ToEmail2));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(ToEmail3));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(ToEmail4));

            String subject = "收款通知";
            mimeMessage.setSubject(subject);
            mimeMessage.setSentDate(new Date());

            StringBuilder sb = new StringBuilder();
            sb.append("您好：\n");
            sb.append("客户下单啦，详情如下：\n\n");
            sb.append("订单时间：" + orderTime + "\n");
            sb.append("订单编号：" + orderNum + "\n\n");
            sb.append("　收货人：" + buyerName + "\n");
            sb.append("联系电话：" + buyerPhone + "\n\n");
            sb.append("订单金额：" + orderPrice + "\n");
            sb.append("订单详情：" + goodsNames + "\n\n");
            sb.append("请前往后台，快速处理此订单，谢谢！\n");


            mimeMessage.setText(sb.toString());
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);

            log.info(subject);
        } catch (Exception i) {
            log.info("---下单邮件通知报错----");
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
