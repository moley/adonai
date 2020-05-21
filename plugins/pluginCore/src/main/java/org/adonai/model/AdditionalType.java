package org.adonai.model;

public enum AdditionalType {
  BACKGROUND("far-images"),
  AUDIO("far-file-audio"),
  VIDEO("far-file-audio"),
  TEXT("far-file-alt");

  String iconName;

  private AdditionalType (String iconName) {
    this.iconName = iconName;
  }

  public String getIconName () {
    return iconName;
  }

}
