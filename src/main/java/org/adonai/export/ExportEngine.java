package org.adonai.export;

import org.adonai.AreaInfo;
import org.adonai.LocationInfo;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.LocationInfoCalculator;
import org.adonai.SizeInfo;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

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


    for (Song nextSong : songs) {

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

      for (SongPart nextPart : nextSong.getSongParts()) {

        if (! nextPart.hasText() && mergedConfiguration.isWithChords() == false)
          continue;

        if (documentBuilder.getExportTokenContainer().hasTokens() && mergedConfiguration.getNewPageStrategy().equals(NewPageStrategy.PER_PART)) {
          documentBuilder.newToken(new ExportTokenNewPage());
          locationInfo = new LocationInfo(mergedConfiguration.getLeftBorder(), mergedConfiguration.getUpperBorder());
        }

        for (Line nextLine : nextPart.getLines()) {
          LocationInfo locationInfoChord = null;
          LocationInfo locationInfoText = new LocationInfo(locationInfo);

          String firstChord = nextLine.getFirstChord();
          if (firstChord != null && exportConfiguration.isWithChords()) {
            locationInfoChord = locationInfoText;
            locationInfoText = new LocationInfo(locationInfoChord.getX(), locationInfoChord.getY() +
              documentBuilder.getSize(firstChord, ExportTokenType.CHORD).getHeight() + exportConfiguration.getChordTextDistance());
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
              maximumLength = Double.max(maximumLength, exportConfiguration.getMinimalChordDistance());
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

        }

        locationInfo = locationInfoCalculator.addY(locationInfo, mergedConfiguration.getInterPartDistance());


      }

      locationInfo = locationInfoCalculator.addY(locationInfo, mergedConfiguration.getInterSongDistance());


    }

    documentBuilder.write(exportFile);



    if (exportFile.exists()) {
      exportFile.setWritable(true);
      exportFile.setExecutable(false);
      System.out.println("Presentation created successfully in " + exportFile.getAbsolutePath());
    }
  }
}
