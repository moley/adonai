package org.adonai.export;

import org.adonai.SizeInfo;

public class ExportConfiguration {

  private Boolean withChords = Boolean.FALSE;

  private Boolean withPartType = Boolean.FALSE;

  private Boolean withTitle = Boolean.FALSE;

  private NewPageStrategy newPageStrategy = NewPageStrategy.PER_SONG;

  private Double interPartDistance = new Double(0);

  private Double interSongDistance = new Double(0);

  private Double interLineDistance = new Double(0);

  private Double chordTextDistance = new Double(0);

  private Double upperBorder = new Double(0);

  private Double leftBorder = new Double(0);

  private Double minimalChordDistance;

  private Boolean openPreview;

  public NewPageStrategy getNewPageStrategy() {
    return newPageStrategy;
  }

  public void setNewPageStrategy(NewPageStrategy newPageStrategy) {
    this.newPageStrategy = newPageStrategy;
  }

  private SizeInfo pageSize;

  public Boolean isWithChords() {
    return withChords;
  }

  public void setWithChords(Boolean withChords) {
    this.withChords = withChords;
  }

  public Boolean isWithPartType() {
    return withPartType;
  }

  public void setWithPartType(Boolean withPartType) {
    this.withPartType = withPartType;
  }

  public SizeInfo getPageSize() {
    return pageSize;
  }

  public void setPageSize(SizeInfo pageSize) {
    this.pageSize = pageSize;
  }


  public Double getInterSongDistance() {
    return interSongDistance;
  }

  public void setInterSongDistance(Double interSongDistance) {
    this.interSongDistance = interSongDistance;
  }

  public Double getInterPartDistance() {
    return interPartDistance;
  }

  public void setInterPartDistance(Double interPartDistance) {
    this.interPartDistance = interPartDistance;
  }

  public Boolean getWithTitle() {
    return withTitle;
  }

  public void setWithTitle(Boolean withTitle) {
    this.withTitle = withTitle;
  }

  public Double getInterLineDistance() {
    return interLineDistance;
  }

  public void setInterLineDistance(Double interLineDistance) {
    this.interLineDistance = interLineDistance;
  }

  public Double getChordTextDistance() {
    return chordTextDistance;
  }

  public void setChordTextDistance(Double chordTextDistance) {
    this.chordTextDistance = chordTextDistance;
  }

  public Double getLeftBorder() {
    return leftBorder;
  }

  public void setLeftBorder(Double leftBorder) {
    this.leftBorder = leftBorder;
  }

  public Double getUpperBorder() {
    return upperBorder;
  }

  public void setUpperBorder(Double upperBorder) {
    this.upperBorder = upperBorder;
  }

  public void setMinimalChordDistance(Double minimalChordDistance) {
    this.minimalChordDistance = minimalChordDistance;
  }

  public Double getMinimalChordDistance() {
    return minimalChordDistance;
  }

  public Boolean getOpenPreview() {
    return openPreview;
  }

  public void setOpenPreview(Boolean openPreview) {
    this.openPreview = openPreview;
  }
}
