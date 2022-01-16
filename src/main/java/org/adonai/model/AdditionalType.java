package org.adonai.model;

public enum AdditionalType {
  BACKGROUND("far-images", false),
  AUDIO("far-file-audio", false),
  VIDEO("far-file-audio", false),
  TEXT("far-file-alt", true);

  String iconName;


  boolean visible;

  private AdditionalType (String iconName, boolean visible) {
    this.iconName = iconName;
    this.visible = visible;
  }

  public String getIconName () {
    return iconName;
  }

  public boolean isVisible() {
    return visible;
  }


}
