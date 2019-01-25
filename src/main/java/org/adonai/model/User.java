package org.adonai.model;

import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlID;
import java.util.UUID;

public class User {

  private String id;

  private SimpleStringProperty username = new SimpleStringProperty();

  private SimpleStringProperty mail = new SimpleStringProperty();

  @XmlID
  public String getId() {
    if (id == null)
      id = UUID.randomUUID().toString();
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username.get();
  }

  public void setUsername(String username) {
    this.username.set(username);
  }

  public SimpleStringProperty usernameProperty () {
    return username;
  }

  public String getMail() {
    return mail.get();
  }

  public void setMail(String mail) {
    this.mail.set(mail);
  }

  public SimpleStringProperty mailProperty () {
    return this.mail;
  }

  public String toString () {
    return username.get();
  }
}
