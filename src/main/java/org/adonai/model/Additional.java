package org.adonai.model;

public class Additional {

  private String link;

  private String cacheLink;

  private AdditionalType additionalType;

  private String content;

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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
