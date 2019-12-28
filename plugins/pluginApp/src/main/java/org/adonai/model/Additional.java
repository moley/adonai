package org.adonai.model;

import java.util.Objects;

public class Additional {

  private String link;

  private String cacheLink;

  private AdditionalType additionalType;

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public AdditionalType getAdditionalType() {
    return additionalType;
  }

  public void setAdditionalType(AdditionalType additionalType) {
    this.additionalType = additionalType;
  }
  public String getCacheLink() {
    return cacheLink;
  }

  public void setCacheLink(String cacheLink) {
    this.cacheLink = cacheLink;
  }
}
