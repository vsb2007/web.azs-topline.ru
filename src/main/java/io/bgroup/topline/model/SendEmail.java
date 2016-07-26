package io.bgroup.topline.model;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail {
    public SendEmail() {
    }

    public void sendMail(Driver driver) {
        if (driver == null){
            System.out.println("Заявка пуста!!!");
            return;
        }
        final String username = "login@gmail.com";
        final String password = "qwe123ASD";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        if (driver != null && driver.getDriverFio() != null && driver.getDriverEmail() != null) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("azsreport2015@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(driver.getDriverEmail()));
                message.setSubject("Вам назначена заявка");
                message.setText("Уважаемый " + driver.getDriverFio() + "!"
                        + "\n\n Вам назначена заявка!");
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
