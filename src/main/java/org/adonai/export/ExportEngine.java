package org.adonai.export;

import org.adonai.AreaInfo;
import org.adonai.LocationInfo;
import org.adonai.model.*;
import org.adonai.LocationInfoCalculator;
import org.adonai.SizeInfo;
import org.adonai.services.SongInfoService;

import java.io.File;
import java.util.Collection;

public class ExportEngine {

  private LocationInfoCalculator locationInfoCalculator = new LocationInfoCalculator();

  private ExportConfigurationMerger exportConfigurationMerger = new ExportConfigurationMerger();

  public void exportSongs (final Collection<Song> songs, final ExportConfiguration exportConfiguration,
                           final File exportFile, final AbstractDocumentBuilder documentBuilder) {

    if (documentBuilder == null)
      throw new IllegalStateException("Parameter documentBuilder must not be null");

    if (documentBuilder.getDefaultConfiguration() == null)
      throw new IllegalStateException("Parameter documentBuilder.getDefaultConfiguration() must not be null");


    ExportConfiguration mergedConfiguration = exportConfigurationMerger.getMergedExportConfiguration(documentBuilder.getDefaultConfiguration(), exportConfiguration);

    LocationInfo locationInfo = new LocationInfo(mergedConfiguration.getLeftBorder(), mergedConfiguration.getUpperBorder());

    SongInfoService songInfoService = new SongInfoService();


    for (Song nextSong : songs) {

      Double maxStructureWidth = getLongestStructureText(documentBuilder, nextSong, mergedConfiguration);

      if (documentBuilder.getExportTokenContainer().hasTokens() && mergedConfiguration.getNewPageStrategy().equals(NewPageStrategy.PER_SONG)) {
        documentBuilder.newToken(new ExportTokenNewPage());
        locationInfo = new LocationInfo(mergedConfiguration.getLeftBorder(), mergedConfiguration.getUpperBorder());
      }

      if (mergedConfiguration.getWithTitle()) {
        String idAndTitle = nextSong.getId() + "     " + nextSong.getTitle();
        SizeInfo sizeInfoTitelAndId = documentBuilder.getSize(idAndTitle, ExportTokenType.TITLE);
        documentBuilder.newToken(new ExportToken(idAndTitle, new AreaInfo(locationInfo, sizeInfoTitelAndId), ExportTokenType.TITLE ));
        locationInfo = locationInfoCalculator.addY(locationInfo, sizeInfoTitelAndId.getHeight() * 2);
      }

      locationInfo = locationInfoCalculator.addY(locationInfo, mergedConfiguration.getTitleSongDistance());

      for (SongPart nextPart : nextSong.getSongParts()) {



        if (! nextPart.hasText() && mergedConfiguration.isWithChords() == false)
          continue;

        if (documentBuilder.getExportTokenContainer().hasTokens() && mergedConfiguration.getNewPageStrategy().equals(NewPageStrategy.PER_PART)) {
          documentBuilder.newToken(new ExportTokenNewPage());
          locationInfo = new LocationInfo(mergedConfiguration.getLeftBorder(), mergedConfiguration.getUpperBorder());
        }

        LocationInfo locationInfoStructure = locationInfo;


        for (Line nextLine : nextPart.getLines()) {
          locationInfo = locationInfoCalculator.addX(locationInfo, maxStructureWidth);

          LocationInfo locationInfoChord = null;
          LocationInfo locationInfoText = new LocationInfo(locationInfo);

          if (exportConfiguration.isWithChords()) {
            locationInfoChord = locationInfoText;
            Double highestChord = getHighestChord(documentBuilder, nextLine);
            locationInfoText = new LocationInfo(locationInfoChord.getX(), locationInfoChord.getY() + highestChord + exportConfiguration.getChordTextDistance());
          }

          if (nextLine.equals(nextPart.getFirstLine())) {
            if (mergedConfiguration.getSongPartDescriptorType() != null && ! mergedConfiguration.getSongPartDescriptorType().equals(SongPartDescriptorStrategy.NONE)) {

              String structure = songInfoService.getStructure(nextSong, nextPart, mergedConfiguration.getSongPartDescriptorType());
              SizeInfo sizeInfoStructure = documentBuilder.getSize(structure, ExportTokenType.STRUCTURE);
              locationInfoStructure = new LocationInfo(locationInfoStructure.getX(), locationInfoText.getY());

              documentBuilder.newToken(new ExportToken(structure, new AreaInfo(locationInfoStructure, sizeInfoStructure), ExportTokenType.STRUCTURE));
            }
          }

          Double heightOfText = new Double(0);

          if (mergedConfiguration.isWithChords()) {

            for (LinePart nextLinePart : nextLine.getLineParts()) {

              SizeInfo sizeInfoText = documentBuilder.getSize(nextLinePart.getText(), ExportTokenType.TEXT);
              Double widthOfText = sizeInfoText.getWidth();
              if (sizeInfoText.getHeight() > heightOfText)
                heightOfText = sizeInfoText.getHeight();

              Double widthOfChord = new Double(0);
              if (nextLinePart.getChord() != null && exportConfiguration.isWithChords()) {
                SizeInfo sizeinfoChord = documentBuilder.getSize(nextLinePart.getChord(), ExportTokenType.CHORD);
                documentBuilder.newToken(new ExportToken(nextLinePart.getChord(), new AreaInfo(locationInfoChord, sizeinfoChord), ExportTokenType.CHORD));

                widthOfChord = sizeinfoChord.getWidth();
              }

              documentBuilder.newToken(new ExportToken(nextLinePart.getText(), new AreaInfo(locationInfoText, sizeInfoText), ExportTokenType.TEXT));

              Double maximumLength = Double.max(widthOfChord, widthOfText);
              if (nextLine.getText() == null || nextLine.getText().trim().isEmpty())
                maximumLength = Double.max(maximumLength, exportConfiguration.getMinimalChordDistance() + widthOfChord);
              if (locationInfoChord != null)
                locationInfoChord = locationInfoCalculator.addX(locationInfoChord, maximumLength);
              locationInfoText = locationInfoCalculator.addX(locationInfoText, maximumLength);
            }
          }
          else { //to avoid empty gaps
            SizeInfo sizeInfoText = documentBuilder.getSize(nextLine.getText(), ExportTokenType.TEXT);
            heightOfText = sizeInfoText.getHeight();
            documentBuilder.newToken(new ExportToken(nextLine.getText(), new AreaInfo(locationInfoText, sizeInfoText), ExportTokenType.TEXT));
          }

          Double interLineDistance = mergedConfiguration.getInterLineDistance();

          locationInfo = new LocationInfo(mergedConfiguration.getLeftBorder(), locationInfoText.getY() + heightOfText + interLineDistance);

          if (!nextLine.equals(nextPart.getLastLine()) && locationInfo.getY() > (documentBuilder.getPageSize().getHeight() - mergedConfiguration.getLowerBorder())) {
            documentBuilder.newToken(new ExportTokenNewPage());
            locationInfo = new LocationInfo(mergedConfiguration.getLeftBorder(), mergedConfiguration.getUpperBorder());
          }

        }

        locationInfo = locationInfoCalculator.addY(locationInfo, mergedConfiguration.getInterPartDistance());


      }

      locationInfo = locationInfoCalculator.addY(locationInfo, mergedConfiguration.getInterSongDistance());


    }

    documentBuilder.write(exportFile);

    if (mergedConfiguration.isOpenPreview())
      documentBuilder.openPreview(exportFile);



    if (exportFile.exists()) {
      exportFile.setWritable(true);
      exportFile.setExecutable(false);
      System.out.println("Presentation created successfully in " + exportFile.getAbsolutePath());
    }
  }

  /**
   * get chord, which need the most y
   *
   * @param documentBuilder
   * @param line
   * @return
   */
  private Double getHighestChord (final DocumentBuilder documentBuilder, final Line line) {
    Double maxChordHeight = 0d;
    for (LinePart next: line.getLineParts()) {
      if (next.getChord() != null && ! next.getChord().trim().isEmpty()) {
        Double currentHeight = documentBuilder.getSize(next.getChord(), ExportTokenType.CHORD).getHeight();
        if (currentHeight > maxChordHeight)
          maxChordHeight = currentHeight;
      }
    }
    return maxChordHeight;
  }

  /**
   * get longest width of structure text
   * @param documentBuilder     documentbuilder
   * @param song                song
   * @param exportConfiguration exportConfiguration
   * @return longest structure text
   */
  private Double getLongestStructureText (final DocumentBuilder documentBuilder, final Song song, ExportConfiguration exportConfiguration) {
    Double maxStructureWidth = 0d;

    SongInfoService songInfoService = new SongInfoService();

    for (SongPart next: song.getSongParts()) {
      String structureText = songInfoService.getStructure(song, next, exportConfiguration.getSongPartDescriptorType());
      Double currentWeight = documentBuilder.getSize(structureText, ExportTokenType.STRUCTURE).getWidth();
      if (currentWeight > maxStructureWidth)
        maxStructureWidth = currentWeight + exportConfiguration.getStructureDistance();
    }

    return maxStructureWidth ;


  }
}
