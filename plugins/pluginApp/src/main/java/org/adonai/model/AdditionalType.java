package org.adonai.model;

public enum AdditionalType {
  BACKGROUND("fa-file-photo-o"),
  AUDIO("fa-file-audio-o"),
  VIDEO("fa-file-video-o"),
  TEXT("fa-file-text-o");

  String iconName;

  private AdditionalType (String iconName) {
    this.iconName = iconName;
  }

  public String getIconName () {
    return iconName;
  }

}
