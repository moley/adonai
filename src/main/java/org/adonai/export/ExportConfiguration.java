package org.adonai.export;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adonai.SizeInfo;

import java.util.UUID;

public class ExportConfiguration {

  private String documentBuilderClass;

  private SimpleStringProperty name = new SimpleStringProperty();

  private String id;

  private Boolean defaultConfiguration = Boolean.FALSE;

  private SimpleBooleanProperty withChords = new SimpleBooleanProperty(false);

  private SimpleBooleanProperty withPartType = new SimpleBooleanProperty(false);

  private SimpleBooleanProperty withTitle = new SimpleBooleanProperty(false);

  private NewPageStrategy newPageStrategy = NewPageStrategy.PER_SONG;

  private SimpleDoubleProperty interPartDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty interSongDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty interLineDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty chordTextDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty upperBorder = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty leftBorder = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty minimalChordDistance = new SimpleDoubleProperty(0);

  private SimpleBooleanProperty openPreview = new SimpleBooleanProperty(false);

  public NewPageStrategy getNewPageStrategy() {
    return newPageStrategy;
  }

  public void setNewPageStrategy(NewPageStrategy newPageStrategy) {
    this.newPageStrategy = newPageStrategy;
  }

  private SizeInfo pageSize;

  //// with chords
  public Boolean isWithChords() {
    return withChords.get();
  }

  public SimpleBooleanProperty withChordsProperty () {
    return withChords;
  }

  public void setWithChords(Boolean withChords) {
    this.withChords.set(withChords);
  }

  //// with part types
  public Boolean isWithPartType() {
    return withPartType.get();
  }

  public void setWithPartType(Boolean withPartType) {
    this.withPartType.set(withPartType);
  }

  public SimpleBooleanProperty withPartTypeProperty () {
    return withPartType;
  }

  //page size
  public SizeInfo getPageSize() {
    return pageSize;
  }

  public void setPageSize(SizeInfo pageSize) {
    this.pageSize = pageSize;
  }


  //inter song distance
  public Double getInterSongDistance() {
    return interSongDistance.getValue();
  }

  public void setInterSongDistance(Double interSongDistance) {
    this.interSongDistance.setValue(interSongDistance);
  }

  public SimpleDoubleProperty interSongDistanceProperty () {
    return interSongDistance;
  }

  //inter part distance
  public Double getInterPartDistance() {
    return interPartDistance.getValue();
  }

  public void setInterPartDistance(Double interPartDistance) {
    this.interPartDistance.setValue(interPartDistance);
  }

  public SimpleDoubleProperty interPartDistance () {
    return interPartDistance;
  }

  //with title
  public Boolean getWithTitle() {
    return withTitle.getValue();
  }

  public void setWithTitle(Boolean withTitle) {
    this.withTitle.setValue(withTitle);
  }

  public SimpleBooleanProperty withTitleProperty () {
    return withTitle;
  }


  //inter line distance
  public Double getInterLineDistance() {
    return interLineDistance.getValue();
  }

  public void setInterLineDistance(Double interLineDistance) {
    this.interLineDistance.setValue(interLineDistance);
  }

  public SimpleDoubleProperty interLineDistanceProperty () {
    return interLineDistance;
  }

  //chord text distance
  public Double getChordTextDistance() {
    return chordTextDistance.getValue();
  }

  public void setChordTextDistance(Double chordTextDistance) {
    this.chordTextDistance.setValue(chordTextDistance);
  }

  public SimpleDoubleProperty chordTextDistanceProperty () {
    return this.chordTextDistance;
  }


  //left border
  public Double getLeftBorder() {
    return leftBorder.getValue();
  }

  public void setLeftBorder(Double leftBorder) {
    this.leftBorder.setValue(leftBorder);
  }

  public SimpleDoubleProperty leftBorderProperty () {
    return leftBorder;
  }

  //upper border
  public Double getUpperBorder() {
    return upperBorder.get();
  }

  public void setUpperBorder(Double upperBorder) {
    this.upperBorder.setValue(upperBorder);
  }

  public SimpleDoubleProperty upperBorderProperty () {
    return upperBorder;
  }

  //minimal chord distance

  public void setMinimalChordDistance(Double minimalChordDistance) {
    this.minimalChordDistance.setValue(minimalChordDistance);
  }

  public Double getMinimalChordDistance() {
    return minimalChordDistance.getValue();
  }

  public SimpleDoubleProperty minimalChordDistanceProperty () {
    return minimalChordDistance;
  }

  //open preview
  public Boolean isOpenPreview() {
    return openPreview.get();
  }

  public void setOpenPreview(Boolean openPreview) {
    this.openPreview.setValue(openPreview);
  }

  public SimpleBooleanProperty openPreviewProperty () {
    return openPreview;
  }

  public String getDocumentBuilderClass() {
    return documentBuilderClass;
  }

  public void setDocumentBuilderClass(String documentBuilderClass) {
    this.documentBuilderClass = documentBuilderClass;
  }

  public String getName() {
    return name.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public SimpleStringProperty nameProperty () {
    return name;
  }

  public String getId() {
    if (id == null)
      id = UUID.randomUUID().toString();
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean isDefaultConfiguration() {
    return defaultConfiguration;
  }

  public void setDefaultConfiguration(Boolean defaultConfiguration) {
    this.defaultConfiguration = defaultConfiguration;
  }
}
