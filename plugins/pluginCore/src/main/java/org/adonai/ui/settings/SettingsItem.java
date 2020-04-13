package org.adonai.ui.settings;

public class SettingsItem {

  private final String id;

  private final String icon;

  private final String name;

  public SettingsItem (final String id, final String name, final String icon) {
    this.id = id;
    this.icon = icon;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String toString () {
    return id;
  }


  public String getIcon() {
    return icon;
  }


  public String getName() {
    return name;
  }


}
