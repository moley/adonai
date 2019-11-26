package org.adonai.export;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adonai.SizeInfo;
import org.adonai.model.SongPartDescriptorStrategy;

import java.util.UUID;

public class ExportConfiguration {

  private String documentBuilderClass;

  private SimpleStringProperty name = new SimpleStringProperty();

  private String id;

  private Boolean defaultConfiguration = Boolean.FALSE;

  private SimpleBooleanProperty withChords = new SimpleBooleanProperty(false);

  private SimpleBooleanProperty withPartType = new SimpleBooleanProperty(false);

  private SimpleBooleanProperty withTitle = new SimpleBooleanProperty(false);

  private SimpleBooleanProperty withContentPage = new SimpleBooleanProperty(false);

  private ReferenceStrategy referenceStrategy = ReferenceStrategy.SHOW_STRUCTURE;

  private NewPageStrategy newPageStrategy = NewPageStrategy.PER_SONG;

  private SongPartDescriptorStrategy songPartDescriptorType = SongPartDescriptorStrategy.NONE;

  private SimpleDoubleProperty interPartDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty interSongDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty interLineDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty chordTextDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty structureDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty upperBorder = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty lowerBorder = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty leftBorder = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty minimalChordDistance = new SimpleDoubleProperty(0);

  private SimpleDoubleProperty titleSongDistance = new SimpleDoubleProperty(0);

  private SimpleBooleanProperty openPreview = new SimpleBooleanProperty(false);

  private SizeInfo pageSize;


  public NewPageStrategy getNewPageStrategy() {
    return newPageStrategy;
  }

  public void setNewPageStrategy(NewPageStrategy newPageStrategy) {
    this.newPageStrategy = newPageStrategy;
  }


  //// with content page
  public Boolean isWithContentPage () {
    return withContentPage.get();
  }
  public SimpleBooleanProperty withContentPageProperty () {
    return withContentPage;
  }
  public void setWithContentPage (final Boolean withContentPage) {
    this.withContentPage.set(withContentPage);
  }


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

  //structure distance (distance between structure and text/chords)
  public Double getStructureDistance () {
    return structureDistance.getValue();
  }

  public void setStructureDistance (Double structureDistance) {
    this.structureDistance.setValue(structureDistance);
  }

  public SimpleDoubleProperty structureDistanceProperty () {return structureDistance;}

  //distance between title and song
  public Double getTitleSongDistance () {
    return titleSongDistance.getValue();
  }

  public void setTitleSongDistance (Double titleSongDistance) {
    this.titleSongDistance.setValue(titleSongDistance);
  }

  public SimpleDoubleProperty titleSongDistacneProperty () {
    return titleSongDistance;
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


  //lower border
  public Double getLowerBorder () {
    return lowerBorder.get();
  }

  public void setLowerBorder(Double lowerBorder) { this.lowerBorder.setValue(lowerBorder);}

  public SimpleDoubleProperty lowerBorderProperty () { return lowerBorder; }

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

  public SongPartDescriptorStrategy getSongPartDescriptorType() {
    return songPartDescriptorType;
  }

  public void setSongPartDescriptorType(SongPartDescriptorStrategy songPartDescriptorType) {
    this.songPartDescriptorType = songPartDescriptorType;
  }

  public ReferenceStrategy getReferenceStrategy() {
    return referenceStrategy;
  }

  public void setReferenceStrategy(ReferenceStrategy referenceStrategy) {
    this.referenceStrategy = referenceStrategy;
  }
}
