package org.adonai.ui.settings;

public class SettingsItem {

  private String id;

  private String icon;



  private String name;

  public SettingsItem (final String id, final String name, final String icon) {
    this.id = id;
    this.icon = icon;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String toString () {
    return id;
  }


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
