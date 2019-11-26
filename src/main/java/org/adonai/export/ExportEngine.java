package org.adonai.export;

import org.adonai.*;
import org.adonai.model.*;
import org.adonai.services.SongInfoService;

import java.io.File;
import java.util.Collection;

public class ExportEngine {

  private LocationInfoCalculator locationInfoCalculator = new LocationInfoCalculator();


  public void exportSongs (final Collection<Song> songs, final ExportConfiguration exportConfiguration,
                           final File exportFile, final AbstractDocumentBuilder documentBuilder) {

    if (documentBuilder == null)
      throw new IllegalStateException("Parameter documentBuilder must not be null");

    if (documentBuilder.getDefaultConfiguration() == null)
      throw new IllegalStateException("Parameter documentBuilder.getDefaultConfiguration() must not be null");



    LocationInfo locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());

    SongInfoService songInfoService = new SongInfoService();

    if (exportConfiguration.isWithContentPage()) {

      for (Song nextSong : songs) {
        String idAndTitle = nextSong.getId() + "     " + nextSong.getTitle();
        SizeInfo sizeInfoTitelAndId = documentBuilder.getSize(idAndTitle, ExportTokenType.TEXT);
        documentBuilder.newToken(new ExportToken(idAndTitle, new AreaInfo(locationInfo, sizeInfoTitelAndId), ExportTokenType.TEXT ));
        locationInfo = locationInfoCalculator.addY(locationInfo, sizeInfoTitelAndId.getHeight() + exportConfiguration.getInterLineDistance());

      }

      documentBuilder.newToken(new ExportTokenNewPage());

    }


    locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());

    for (Song nextSong : songs) {

      Double maxStructureWidth = getLongestStructureText(documentBuilder, nextSong, exportConfiguration);

      if (documentBuilder.getExportTokenContainer().hasTokens() && exportConfiguration.getNewPageStrategy().equals(NewPageStrategy.PER_SONG)) {
        documentBuilder.newToken(new ExportTokenNewPage());
        locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());
      }

      if (exportConfiguration.getWithTitle()) {
        String realKey = nextSong.getCurrentKey() != null ? nextSong.getCurrentKey() : nextSong.getOriginalKey();
        realKey = realKey != null ? "(" + realKey + ")": "";
        String idAndTitle = nextSong.getId() + "     " + nextSong.getTitle() + "                   " + realKey;

        SizeInfo sizeInfoTitelAndId = documentBuilder.getSize(idAndTitle, ExportTokenType.TITLE);
        documentBuilder.newToken(new ExportToken(idAndTitle, new AreaInfo(locationInfo, sizeInfoTitelAndId), ExportTokenType.TITLE ));
        locationInfo = locationInfoCalculator.addY(locationInfo, sizeInfoTitelAndId.getHeight() * 2);
      }

      locationInfo = locationInfoCalculator.addY(locationInfo, exportConfiguration.getTitleSongDistance());

      for (SongPart nextPart : nextSong.getSongParts()) {

        if (nextPart.getReferencedSongPart() != null && exportConfiguration.getReferenceStrategy().equals(ReferenceStrategy.SHOW_STRUCTURE)) {
          SongPart referencedSongpart = nextSong.findSongPartByUUID(nextPart.getReferencedSongPart());
          String structure = songInfoService.getStructure(nextSong, referencedSongpart, exportConfiguration.getSongPartDescriptorType());
          SizeInfo sizeInfoStructure = documentBuilder.getSize(structure, ExportTokenType.STRUCTURE);
          documentBuilder.newToken(new ExportToken(structure, new AreaInfo(locationInfo, sizeInfoStructure), ExportTokenType.STRUCTURE));
          locationInfo = locationInfoCalculator.addY(locationInfo, exportConfiguration.getInterPartDistance() + sizeInfoStructure.getHeight());

        } else {

          if (!nextPart.hasText() && exportConfiguration.isWithChords() == false)
            continue;

          if (documentBuilder.getExportTokenContainer().hasTokens() && exportConfiguration.getNewPageStrategy().equals(NewPageStrategy.PER_PART)) {
            documentBuilder.newToken(new ExportTokenNewPage());
            locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());
          }

          LocationInfo locationInfoStructure = locationInfo;


          for (Line nextLine : nextPart.getLines()) {
            locationInfo = locationInfoCalculator.addX(locationInfo, maxStructureWidth);

            LocationInfo locationInfoChord = null;
            LocationInfo locationInfoText = new LocationInfo(locationInfo);

            if (exportConfiguration.isWithChords()) {
              locationInfoChord = locationInfoText;
              Double highestChord = getHighestChord(documentBuilder, nextLine, nextSong, exportConfiguration);
              locationInfoText = new LocationInfo(locationInfoChord.getX(), locationInfoChord.getY() + highestChord + exportConfiguration.getChordTextDistance());
            }

            if (nextLine.equals(nextPart.getFirstLine())) {
              if (exportConfiguration.getSongPartDescriptorType() != null && !exportConfiguration.getSongPartDescriptorType().equals(SongPartDescriptorStrategy.NONE)) {

                String structure = songInfoService.getStructure(nextSong, nextPart, exportConfiguration.getSongPartDescriptorType());
                SizeInfo sizeInfoStructure = documentBuilder.getSize(structure, ExportTokenType.STRUCTURE);
                locationInfoStructure = new LocationInfo(locationInfoStructure.getX(), locationInfoText.getY());

                documentBuilder.newToken(new ExportToken(structure, new AreaInfo(locationInfoStructure, sizeInfoStructure), ExportTokenType.STRUCTURE));
              }
            }

            Double heightOfText = new Double(0);

            if (exportConfiguration.isWithChords()) {

              for (LinePart nextLinePart : nextLine.getLineParts()) {

                SizeInfo sizeInfoText = documentBuilder.getSize(nextLinePart.getText(), ExportTokenType.TEXT);
                Double widthOfText = sizeInfoText.getWidth();
                if (sizeInfoText.getHeight() > heightOfText)
                  heightOfText = sizeInfoText.getHeight();

                Double widthOfChord = new Double(0);
                if (nextLinePart.getChord() != null && ! nextLinePart.getChord().trim().isEmpty() && exportConfiguration.isWithChords()) {

                  String eventuallyTransformedChord = transposeChordOnDemand(nextLinePart, nextSong, exportConfiguration);

                  SizeInfo sizeinfoChord = documentBuilder.getSize(eventuallyTransformedChord, ExportTokenType.CHORD);
                  documentBuilder.newToken(new ExportToken(eventuallyTransformedChord, new AreaInfo(locationInfoChord, sizeinfoChord), ExportTokenType.CHORD));

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
            } else { //to avoid empty gaps
              SizeInfo sizeInfoText = documentBuilder.getSize(nextLine.getText(), ExportTokenType.TEXT);
              heightOfText = sizeInfoText.getHeight();
              documentBuilder.newToken(new ExportToken(nextLine.getText(), new AreaInfo(locationInfoText, sizeInfoText), ExportTokenType.TEXT));
            }

            Double interLineDistance = exportConfiguration.getInterLineDistance();

            locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), locationInfoText.getY() + heightOfText + interLineDistance);

            if (documentBuilder.getPageSize() != null) {
              if (!nextLine.equals(nextPart.getLastLine()) && locationInfo.getY() > (documentBuilder.getPageSize().getHeight() - exportConfiguration.getLowerBorder())) {
                documentBuilder.newToken(new ExportTokenNewPage());
                locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());
              }
            }

          }

          locationInfo = locationInfoCalculator.addY(locationInfo, exportConfiguration.getInterPartDistance());


        }


      }

      locationInfo = locationInfoCalculator.addY(locationInfo, exportConfiguration.getInterSongDistance());


    }

    documentBuilder.write(exportFile);

    if (exportConfiguration.isOpenPreview())
      documentBuilder.openPreview(exportFile);



    if (exportFile.exists()) {
      exportFile.setWritable(true);
      exportFile.setExecutable(false);
      System.out.println("Presentation created successfully in " + exportFile.getAbsolutePath());
    }
  }

  private String transposeChordOnDemand (LinePart linePart, Song song, ExportConfiguration mergedConfiguration) {
    Chord chord = new Chord(linePart.getChord());
    return chord.toString();
  }

  /**
   * get chord, which need the most y
   *
   * @param documentBuilder
   * @param line
   * @return
   */
  private Double getHighestChord (final DocumentBuilder documentBuilder, final Line line, Song song, ExportConfiguration mergedConfiguration) {
    Double maxChordHeight = 0d;
    for (LinePart next: line.getLineParts()) {
      if (next.getChord() != null && ! next.getChord().trim().isEmpty()) {
        String eventuallyTransformedChord = transposeChordOnDemand(next, song, mergedConfiguration);
        Double currentHeight = documentBuilder.getSize(eventuallyTransformedChord, ExportTokenType.CHORD).getHeight();
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
   * @param mergedConfiguration mergedConfiguration
   * @return longest structure text
   */
  private Double getLongestStructureText (final DocumentBuilder documentBuilder, final Song song, ExportConfiguration mergedConfiguration) {

    if (mergedConfiguration.getSongPartDescriptorType() == null || mergedConfiguration.getSongPartDescriptorType().equals(SongPartDescriptorStrategy.NONE))
      return Double.valueOf(0);

    Double maxStructureWidth = 0d;

    SongInfoService songInfoService = new SongInfoService();

    for (SongPart next: song.getSongParts()) {
      String structureText = songInfoService.getStructure(song, next, mergedConfiguration.getSongPartDescriptorType());
      Double currentWeight = documentBuilder.getSize(structureText, ExportTokenType.STRUCTURE).getWidth();
      if (currentWeight > maxStructureWidth)
        maxStructureWidth = currentWeight + mergedConfiguration.getStructureDistance();
    }

    return maxStructureWidth ;


  }
}
