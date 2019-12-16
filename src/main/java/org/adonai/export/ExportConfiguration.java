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
  private SimpleBooleanProperty withChordsInitialized = new SimpleBooleanProperty(false);

  private SimpleBooleanProperty withTitle = new SimpleBooleanProperty(false);
  private SimpleBooleanProperty withTitleInitialized = new SimpleBooleanProperty(false);

  private SimpleBooleanProperty withContentPage = new SimpleBooleanProperty(false);
  private SimpleBooleanProperty withContentPageInitialized = new SimpleBooleanProperty(false);

  private SimpleBooleanProperty withIndexPage = new SimpleBooleanProperty(false);
  private SimpleBooleanProperty withIndexPageInitialized = new SimpleBooleanProperty(false);

  private SimpleBooleanProperty openPreview = new SimpleBooleanProperty(false);
  private SimpleBooleanProperty openPreviewInitialized = new SimpleBooleanProperty(false);

  private ReferenceStrategy referenceStrategy = ReferenceStrategy.SHOW_STRUCTURE;

  private NewPageStrategy newPageStrategy = NewPageStrategy.PER_SONG;

  private SongPartDescriptorStrategy songPartDescriptorType = SongPartDescriptorStrategy.NONE;

  private SimpleDoubleProperty interPartDistance = new SimpleDoubleProperty(-1);

  private SimpleDoubleProperty interSongDistance = new SimpleDoubleProperty(-1);

  private SimpleDoubleProperty interLineDistance = new SimpleDoubleProperty(-1);

  private SimpleDoubleProperty chordTextDistance = new SimpleDoubleProperty(-1);

  private SimpleDoubleProperty structureDistance = new SimpleDoubleProperty(-1);

  private SimpleDoubleProperty upperBorder = new SimpleDoubleProperty(-1);

  private SimpleDoubleProperty lowerBorder = new SimpleDoubleProperty(-1);

  private SimpleDoubleProperty leftBorder = new SimpleDoubleProperty(-1);

  private SimpleDoubleProperty minimalChordDistance = new SimpleDoubleProperty(-1);

  private SimpleDoubleProperty titleSongDistance = new SimpleDoubleProperty(-1);



  private SizeInfo pageSize;


  public NewPageStrategy getNewPageStrategy() {
    return newPageStrategy;
  }

  public void setNewPageStrategy(NewPageStrategy newPageStrategy) {
    this.newPageStrategy = newPageStrategy;
  }

  //with index page
  public Boolean isWithIndexPage () {
    return withIndexPage.get();
  }

  public boolean isWithIndexPageInitialized () {
    return withIndexPageInitialized.get();
  }

  public SimpleBooleanProperty withIndexPageProperty () {
    return withIndexPage;
  }

  public SimpleBooleanProperty withIndexPageInitializedProperty () {
    return withIndexPageInitialized;
  }

  public void setWithIndexPage (final Boolean withIndexPage) {
    this.withIndexPageInitialized.set(Boolean.TRUE);
    this.withIndexPage.set(withIndexPage);
  }

  //// with content page
  public Boolean isWithContentPage () {
    return withContentPage.get();
  }

  public boolean isWithContentPageInitialized () {
    return withContentPageInitialized.get();
  }

  public SimpleBooleanProperty withContentPageInitializedProperty () {
    return withContentPageInitialized;
  }


  public SimpleBooleanProperty withContentPageProperty () {
    return withContentPage;
  }
  public void setWithContentPage (final Boolean withContentPage) {
    this.withContentPageInitialized.set(Boolean.TRUE);
    this.withContentPage.set(withContentPage);
  }

  //with title
  public Boolean getWithTitle() {
    return withTitle.getValue();
  }

  public boolean isWithTitleInitialized () {
    return withTitleInitialized.get();
  }

  public SimpleBooleanProperty withTitleInitializedProperty () {
    return withTitleInitialized;
  }

  public void setWithTitle(Boolean withTitle) {
    this.withTitleInitialized.set(Boolean.TRUE);
    this.withTitle.setValue(withTitle);
  }

  public SimpleBooleanProperty withTitleProperty () {
    return withTitle;
  }

  //open preview
  public Boolean isOpenPreview() {
    return openPreview.get();
  }

  public void setOpenPreview(Boolean openPreview) {
    this.openPreviewInitialized.set(Boolean.TRUE);
    this.openPreview.setValue(openPreview);
  }

  public SimpleBooleanProperty openPreviewProperty () {
    return openPreview;
  }

  public SimpleBooleanProperty getOpenPreviewInitializedProperty () {
    return openPreviewInitialized;
  }

  public boolean isOpenPreviewInitialized () {
    return openPreviewInitialized.get();
  }

  //// with chords
  public Boolean isWithChords() {
    return withChords.get();
  }

  public SimpleBooleanProperty withChordsProperty () {
    return withChords;
  }

  public SimpleBooleanProperty getWithChordsInitializedProperty () {
    return withChordsInitialized;
  }

  public void setWithChords(Boolean withChords) {
    this.withChordsInitialized.set(Boolean.TRUE);
    this.withChords.set(withChords);
  }

  public boolean isWithChordsInitialized () {
    return withChordsInitialized.get();
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

  @Override public String toString() {
    return "ExportConfiguration{" + "documentBuilderClass='" + documentBuilderClass + '\'' + "\n name=" + name + "\n id='" + id +
        '\'' + "\n defaultConfiguration=" + defaultConfiguration + "\n withChords=" + withChords + "\n withTitle=" + withTitle +
        "\n withContentPage=" + withContentPage + "\n withIndexPage=" + withIndexPage + "\n referenceStrategy=" + referenceStrategy +
        "\n newPageStrategy=" + newPageStrategy + "\n songPartDescriptorType=" + songPartDescriptorType + "\n interPartDistance=" +
        interPartDistance + "\n interSongDistance=" + interSongDistance + "\n interLineDistance=" + interLineDistance + "\n chordTextDistance="+
        chordTextDistance + "\n structureDistance=" + structureDistance + "\n upperBorder=" + upperBorder + "\n lowerBorder=" + lowerBorder +
        "\n leftBorder=" + leftBorder + "\n minimalChordDistance=" + minimalChordDistance + "\n titleSongDistance=" + titleSongDistance +
        "\n openPreview=" + openPreview + "\n pageSize=" + pageSize + '}';
  }
}
