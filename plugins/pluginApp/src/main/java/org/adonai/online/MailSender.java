package org.adonai.online;

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

    String missingMessage = "";

    AdonaiProperties adonaiProperties = new AdonaiProperties();
    Properties prop = new Properties();
    final String username = adonaiProperties.getProperty("adonai.smtp.username");
    if (username == null)
      missingMessage += "- adonai.smtp.username is missing. Please configure a valid username\n";

    final String password = adonaiProperties.getProperty("adonai.smtp.password");
    if (password == null)
      missingMessage += "- adonai.smtp.password is missing. Please configure a valid password\n";

    final String host = adonaiProperties.getProperty("adonai.smtp.host");
    if (host == null)
      missingMessage += "- adonai.smtp.host is missing. Please configure a valid smtp host\n";

    final String port = adonaiProperties.getProperty("adonai.smtp.port");
    if (port == null)
      missingMessage += "- adonai.smtp.port is missing. Please configure a valid smtp port\n";

    final String auth = adonaiProperties.getProperty("adonai.smtp.auth");
    if (auth == null)
      missingMessage += "- adonai.smtp.auth is missing. Please configure by true/false if authentication over AUTH command should be enabled\n";

    if (! missingMessage.trim().isEmpty())
      throw new IllegalStateException("Configuration is missing:\n" + missingMessage);

    final String sslEnable = adonaiProperties.getProperty("adonai.smtp.ssl.enable");
    final String debug = adonaiProperties.getProperty("adonai.smtp.debug", Boolean.TRUE.toString());
    prop.put("mail.smtp.host", host);
    prop.put("mail.smtp.port", port);
    prop.put("mail.smtp.auth", auth);
    prop.put("mail.smtp.ssl.enable", sslEnable);
    prop.put("mail.smtp.debug", debug);

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
