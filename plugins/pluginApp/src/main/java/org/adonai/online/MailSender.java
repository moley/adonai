package org.adonai.online;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.adonai.AdonaiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {

  private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);

  public void sendExportMail(final List<String> mails, final Collection<String> links) {
    if (links == null || links.isEmpty())
      throw new IllegalStateException("Links must be not null and contain at least one entry");

    AdonaiProperties adonaiProperties = new AdonaiProperties();
    Properties prop = new Properties();
    final String username = adonaiProperties.getProperty("adonai.smtp.username");
    final String password = adonaiProperties.getProperty("adonai.smtp.password");
    prop.put("mail.smtp.host", adonaiProperties.getProperty("adonai.smtp.host"));
    prop.put("mail.smtp.port", adonaiProperties.getProperty("adonai.smtp.port"));
    prop.put("mail.smtp.auth", adonaiProperties.getProperty("adonai.smtp.auth"));
    prop.put("mail.smtp.ssl.enable", adonaiProperties.getProperty("adonai.smtp.ssl.enable"));
    prop.put("mail.smtp.debug", "true");

    prop.put("mail.smtp.socketFactory.port", "465");
    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    prop.put("mail.smtp.socketFactory.fallback", "false");
    prop.put("mail.smtp.starttls.enable", "true");

    if (mails.isEmpty())
      mails.add(username);

    String recieverList = String.join(", ", mails);

    LOGGER.info("Username      : " + username);
    LOGGER.info("Password      : " + password);
    LOGGER.info("Recieverlist  : " + recieverList);
    LOGGER.info("Properties    : " + prop);

    Session session = Session.getInstance(prop,

        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });

    session.setDebug(true);

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("adonai@gmail.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recieverList));
      message.setSubject("New exported songbook");
      message.setText("Hi, " + "\n\n A new songbook is exported under \n\n" + String.join("\n", links));

      Transport.send(message);

      LOGGER.info("Done ");
    } catch (MessagingException e) {
      e.printStackTrace();
    }

  }
}
