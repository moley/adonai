package org.adonai.export;

import org.adonai.AreaInfo;

public class ExportToken {

  private String text;

  private AreaInfo areaInfo;

  private ExportTokenType exportTokenType;


  public ExportToken(final String text, final AreaInfo areaInfo, final ExportTokenType exportTokenType) {
    this.text = text;
    this.areaInfo = areaInfo;
    this.exportTokenType = exportTokenType;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  public AreaInfo getAreaInfo() {
    return areaInfo;
  }

  public void setAreaInfo(AreaInfo areaInfo) {
    this.areaInfo = areaInfo;
  }

  public ExportTokenType getExportTokenType () {
    return exportTokenType;
  }


  public String toString () {
    return text + "(" + areaInfo + ")";

  }
}
