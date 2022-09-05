package org.adonai.fx.settings;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.adonai.model.Model;

public class SettingsMailConfigurationsController extends AbstractSettingsController{

  public TextField txtSmtpHost;
  public TextField txtSmtpPort;
  public TextField txtSmtpUsername;
  public TextField txtSmtpPassword;
  public CheckBox chkAuthenticated;
  public CheckBox chkSSL;

  @Override public void setModel(Model model) {
    super.setModel(model);
    //TODO txtSmtpHost.setText(adonaiProperties.getProperty(MailSender.ADONAI_SMTP_HOST));
    //TODO txtSmtpPort.setText(adonaiProperties.getProperty(MailSender.ADONAI_SMTP_PORT));
    //TODO txtSmtpUsername.setText(adonaiProperties.getProperty(MailSender.ADONAI_SMTP_USERNAME));
    //TODO txtSmtpPassword.setText(adonaiProperties.getProperty(MailSender.ADONAI_SMTP_PASSWORD));
    //TODO chkAuthenticated.setSelected(adonaiProperties.getPropertyBoolean(MailSender.ADONAI_SMTP_AUTH, true));
    //TODO chkSSL.setSelected(adonaiProperties.getPropertyBoolean(MailSender.ADONAI_SMTP_SSL_ENABLE, true));
    
  }
}
