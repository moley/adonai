package org.adonai.online;

import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.adonai.AdonaiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class MailSender {

  public static final String ADONAI_SMTP_PASSWORD = "adonai.smtp.password";
  public static final String ADONAI_SMTP_HOST = "adonai.smtp.host";
  public static final String ADONAI_SMTP_USERNAME = "adonai.smtp.username";
  public static final String ADONAI_SMTP_PORT = "adonai.smtp.port";
  public static final String ADONAI_SMTP_AUTH = "adonai.smtp.auth";
  public static final String ADONAI_SMTP_SSL_ENABLE = "adonai.smtp.ssl.enable";

  public void sendMail(final List<String> adresses, final String subject, final List<String> text) {
    String missingMessage = "";

    AdonaiProperties adonaiProperties = new AdonaiProperties();
    Properties prop = new Properties();
    final String username = adonaiProperties.getProperty(ADONAI_SMTP_USERNAME);
    if (username == null)
      missingMessage += "- adonai.smtp.username is missing. Please configure a valid username\n";

    final String password = adonaiProperties.getProperty(ADONAI_SMTP_PASSWORD);
    if (password == null)
      missingMessage += "- adonai.smtp.password is missing. Please configure a valid password\n";

    final String host = adonaiProperties.getProperty(ADONAI_SMTP_HOST);
    if (host == null)
      missingMessage += "- adonai.smtp.host is missing. Please configure a valid smtp host\n";

    final String port = adonaiProperties.getProperty(ADONAI_SMTP_PORT);
    if (port == null)
      missingMessage += "- adonai.smtp.port is missing. Please configure a valid smtp port\n";

    final String auth = adonaiProperties.getProperty(ADONAI_SMTP_AUTH);
    if (auth == null)
      missingMessage += "- adonai.smtp.auth is missing. Please configure by true/false if authentication over AUTH command should be enabled\n";

    if (! missingMessage.trim().isEmpty())
      throw new IllegalStateException("Configuration is missing:\n" + missingMessage);

    final String sslEnable = adonaiProperties.getProperty(ADONAI_SMTP_SSL_ENABLE);
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

    if (adresses.isEmpty())
      adresses.add(username);

    String recieverList = String.join(", ", adresses);

    log.info("Username      : " + username);
    log.info("Password      : " + password);
    log.info("Recieverlist  : " + recieverList);
    log.info("Properties    : " + prop);

    Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });

    session.setDebug(true);

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("adonai@gmail.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recieverList));
      message.setSubject(subject);
      message.setText(String.join("\n", text));
      Transport.send(message);

      log.info("Done ");
    } catch (MessagingException e) {
      log.error(e.getLocalizedMessage(), e);
    }

  }
}
