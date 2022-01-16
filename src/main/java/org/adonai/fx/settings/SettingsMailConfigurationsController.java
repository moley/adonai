package org.adonai.fx.settings;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.adonai.AdonaiProperties;
import org.adonai.model.Model;
import org.adonai.online.MailSender;

public class SettingsMailConfigurationsController extends AbstractSettingsController{

  public TextField txtSmtpHost;
  public TextField txtSmtpPort;
  public TextField txtSmtpUsername;
  public TextField txtSmtpPassword;
  public CheckBox chkAuthenticated;
  public CheckBox chkSSL;

  @Override public void setModel(Model model) {
    super.setModel(model);
    AdonaiProperties adonaiProperties = getApplicationEnvironment().getAdonaiProperties();
    txtSmtpHost.setText(adonaiProperties.getProperty(MailSender.ADONAI_SMTP_HOST));
    txtSmtpPort.setText(adonaiProperties.getProperty(MailSender.ADONAI_SMTP_PORT));
    txtSmtpUsername.setText(adonaiProperties.getProperty(MailSender.ADONAI_SMTP_USERNAME));
    txtSmtpPassword.setText(adonaiProperties.getProperty(MailSender.ADONAI_SMTP_PASSWORD));
    chkAuthenticated.setSelected(adonaiProperties.getPropertyBoolean(MailSender.ADONAI_SMTP_AUTH, true));
    chkSSL.setSelected(adonaiProperties.getPropertyBoolean(MailSender.ADONAI_SMTP_SSL_ENABLE, true));
    
  }
}
