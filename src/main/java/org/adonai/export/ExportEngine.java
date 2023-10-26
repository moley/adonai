package org.adonai.export;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.adonai.AreaInfo;
import org.adonai.Key;
import org.adonai.LocationInfo;
import org.adonai.LocationInfoCalculator;
import org.adonai.Note;
import org.adonai.SizeInfo;
import org.adonai.fx.editcontent.KeyType;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartDescriptorStrategy;
import org.adonai.model.SongStructItem;
import org.adonai.services.SongInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportEngine {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExportEngine.class);

  private LocationInfoCalculator locationInfoCalculator = new LocationInfoCalculator();

  public void exportSongs(final Collection<Song> songs, final ExportConfiguration exportConfiguration,
      final File exportFile, final AbstractDocumentBuilder documentBuilder) {

    if (documentBuilder == null)
      throw new IllegalStateException("Parameter documentBuilder must not be null");

    if (documentBuilder.getDefaultConfiguration() == null)
      throw new IllegalStateException("Parameter documentBuilder.getDefaultConfiguration() must not be null");

    LocationInfo locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(),
        exportConfiguration.getUpperBorder());

    SongInfoService songInfoService = new SongInfoService();

    if (exportConfiguration.isWithContentPage()) {

      for (Song nextSong : songs) {
        if (nextSong.isDisabled())
          continue;

        String idAndTitle = nextSong.getId() + "     " + nextSong.getTitle();
        SizeInfo sizeInfoTitelAndId = documentBuilder.getSize(idAndTitle, ExportTokenType.TEXT);
        documentBuilder.newToken(new ExportToken(null, null, idAndTitle, new AreaInfo(locationInfo, sizeInfoTitelAndId),
            ExportTokenType.TEXT));
        locationInfo = locationInfoCalculator.addY(locationInfo,
            sizeInfoTitelAndId.getHeight() + exportConfiguration.getInterLineDistance());

        if (documentBuilder.getPageSize() != null) {
          if (locationInfo.getY() > (documentBuilder.getPageSize()
              .getHeight() - exportConfiguration.getLowerBorder()) - (sizeInfoTitelAndId.getHeight() * 3)) {
            documentBuilder.newToken(new ExportTokenNewPage(nextSong));
            locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());
          }
        }

      }

      documentBuilder.newToken(new ExportTokenNewPage(null));
      locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());

    }

    if (exportConfiguration.isWithIndexPage()) {

      List<Song> sortedSongs = new ArrayList<Song>(songs);
      sortedSongs.sort(Comparator.comparing(Song::getTitle));

      for (Song nextSong : sortedSongs) {
        if (nextSong.isDisabled())
          continue;

        String idAndTitle = String.format("%.80s  %2d", nextSong.getTitle(), nextSong.getId());
        SizeInfo sizeInfoTitelAndId = documentBuilder.getSize(idAndTitle, ExportTokenType.TEXT);
        documentBuilder.newToken(new ExportToken(null, null, idAndTitle, new AreaInfo(locationInfo, sizeInfoTitelAndId),
            ExportTokenType.TEXT));
        locationInfo = locationInfoCalculator.addY(locationInfo,
            sizeInfoTitelAndId.getHeight() + exportConfiguration.getInterLineDistance());

        if (documentBuilder.getPageSize() != null) {
          if (locationInfo.getY() > (documentBuilder.getPageSize()
              .getHeight() - exportConfiguration.getLowerBorder()) - (sizeInfoTitelAndId.getHeight() * 3)) {
            documentBuilder.newToken(new ExportTokenNewPage(nextSong));
            locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());
          }
        }

      }

      documentBuilder.newToken(new ExportTokenNewPage(null));
      locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());

    }

    for (Song nextSong : songs) {
      if (nextSong.isDisabled())
        continue;

      Double maxStructureWidth = getLongestStructureText(documentBuilder, nextSong, exportConfiguration);

      if (documentBuilder.getExportTokenContainer().hasTokens() && exportConfiguration.getNewPageStrategy()
          .equals(NewPageStrategy.PER_SONG)) {
        documentBuilder.newToken(new ExportTokenNewPage(nextSong));
        locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), exportConfiguration.getUpperBorder());
      }

      String originalKey = (exportConfiguration.getWithKeys() && nextSong.getOriginalKey() != null ?
          nextSong.getOriginalKey() :
          "");
      String realKey = (exportConfiguration.getWithKeys() && nextSong.getCurrentKey() != null ?
          nextSong.getCurrentKey() :
          "");
      String completeKey = (exportConfiguration.getWithKeys() ? " | " + originalKey + " -> " + realKey : "");
      String leadVoice = (exportConfiguration.getWithLead() && nextSong.getLeadVoice() != null ?
          " | " + nextSong.getLeadVoice().getUsername() :
          "");

      String id = (exportConfiguration.getWithId() && nextSong.getId() != null) ? nextSong.getId().toString() : "";
      String title = (exportConfiguration.getWithTitle() && nextSong.getTitle() != null) ?
          nextSong.getTitle().toString() :
          "";


      String capo = "";
      if (exportConfiguration.getKeyType().equals(KeyType.CURRENT_CAPO)) {
        Note noteFrom = Note.from(Key.fromString(nextSong.getCurrentKey()));
        Note noteTo = Note.from(Key.fromString(nextSong.getCurrentKeyCapo()));

        int fromIndex = noteFrom.ordinal();
        int toIndex = noteTo.ordinal();

        int diff = toIndex - fromIndex;
        if (diff == 0)
          capo = " | Ohne Capo ";
        else
          capo = " | Capo " + Math.abs(diff);
      }

      String idAndTitle = id + "     " + title;
      SizeInfo sizeInfoTitelAndId = documentBuilder.getSize(idAndTitle, ExportTokenType.TITLE);
      documentBuilder.newToken(
          new ExportToken(nextSong, null, idAndTitle, new AreaInfo(locationInfo, sizeInfoTitelAndId),
             ExportTokenType.TITLE));
      locationInfo = locationInfoCalculator.addX(locationInfo, sizeInfoTitelAndId.getWidth() + 10);


      String headerInfo = capo + completeKey + leadVoice;
      if (! headerInfo.trim().isEmpty())
        headerInfo = (headerInfo  + " |").trim();

      if (!headerInfo.trim().isEmpty()) {
        SizeInfo sizeInfoheaderInfo = documentBuilder.getSize(headerInfo, ExportTokenType.TEXT);
        documentBuilder.newToken(
            new ExportToken(nextSong, null, headerInfo, new AreaInfo(locationInfo, sizeInfoheaderInfo),
                ExportTokenType.TEXT));
      }
      locationInfo = locationInfoCalculator.addY(locationInfo, sizeInfoTitelAndId.getHeight() * 2);
      locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), locationInfo.getY());

      locationInfo = locationInfoCalculator.addY(locationInfo, exportConfiguration.getTitleSongDistance());

      for (SongStructItem nextStructItem : nextSong.getStructItems()) {

        SongPart nextPart = nextSong.findSongPart(nextStructItem);

        if (exportConfiguration.isWithRemarks() != null && exportConfiguration.isWithRemarks().equals(Boolean.TRUE)) {
          if (nextStructItem.getRemarks() != null && !nextStructItem.getRemarks().trim().isEmpty()) {

            String remarks = nextStructItem.getRemarks();
            SizeInfo sizeinfoRemarks = documentBuilder.getSize(remarks, ExportTokenType.REMARKS);
            if (exportConfiguration.isRemarksRight()) {
              LocationInfo locationInfoRemarks = new LocationInfo(documentBuilder.getPageSize()
                  .getWidth() - exportConfiguration.getLeftBorder() - sizeinfoRemarks.getWidth(), locationInfo.getY());
              ExportToken remarksToken = new ExportToken(nextSong, nextStructItem, remarks,
                  new AreaInfo(locationInfoRemarks, sizeinfoRemarks), ExportTokenType.REMARKS);
              documentBuilder.newToken(remarksToken);
              locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(), locationInfo.getY());
            } else {
              ExportToken remarksToken = new ExportToken(nextSong, nextStructItem, remarks,
                  new AreaInfo(locationInfo, sizeinfoRemarks), ExportTokenType.REMARKS);
              documentBuilder.newToken(remarksToken);
              locationInfo = locationInfoCalculator.addY(locationInfo, sizeinfoRemarks.getHeight());

            }
            //TODO make configurable rightbound one line or leftbound nextline
          }
        }

        if (!nextStructItem.isFirstOccurence() && exportConfiguration.getReferenceStrategy()
            .equals(ReferenceStrategy.SHOW_STRUCTURE)) {
          String structure = songInfoService.getStructure(nextSong, nextStructItem,
              exportConfiguration.getSongPartDescriptorType());
          SizeInfo sizeInfoStructure = documentBuilder.getSize(structure, ExportTokenType.STRUCTURE);
          documentBuilder.newToken(
              new ExportToken(nextSong, nextStructItem, structure, new AreaInfo(locationInfo, sizeInfoStructure),
                  ExportTokenType.STRUCTURE));
          locationInfo = locationInfoCalculator.addY(locationInfo,
              exportConfiguration.getInterPartDistance() + sizeInfoStructure.getHeight());

        } else {

          if (!nextPart.hasText() && !exportConfiguration.isWithChords() && exportConfiguration.getSongPartDescriptorType()
              .equals(SongPartDescriptorStrategy.NONE))
            continue;

          if (documentBuilder.getExportTokenContainer().hasTokens() && exportConfiguration.getNewPageStrategy()
              .equals(NewPageStrategy.PER_PART)) {
            documentBuilder.newToken(new ExportTokenNewPage(nextSong));
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
              locationInfoText = new LocationInfo(locationInfoChord.getX(),
                  locationInfoChord.getY() + highestChord + exportConfiguration.getChordTextDistance());
            }

            if (nextLine.equals(nextPart.getFirstLine())) {
              if (exportConfiguration.getSongPartDescriptorType() != null && !exportConfiguration.getSongPartDescriptorType()
                  .equals(SongPartDescriptorStrategy.NONE)) {

                String structure = songInfoService.getStructure(nextSong, nextStructItem,
                    exportConfiguration.getSongPartDescriptorType());
                SizeInfo sizeInfoStructure = documentBuilder.getSize(structure, ExportTokenType.STRUCTURE);
                locationInfoStructure = new LocationInfo(locationInfoStructure.getX(), locationInfoText.getY());

                documentBuilder.newToken(new ExportToken(nextSong, nextStructItem, structure,
                    new AreaInfo(locationInfoStructure, sizeInfoStructure), ExportTokenType.STRUCTURE));
              }
            }

            Double heightOfText = (double) 0;

            if (exportConfiguration.isWithChords()) {

              for (LinePart nextLinePart : nextLine.getLineParts()) {

                SizeInfo sizeInfoText = documentBuilder.getSize(nextLinePart.getText(), ExportTokenType.TEXT);
                Double widthOfText = sizeInfoText.getWidth();
                if (sizeInfoText.getHeight() > heightOfText)
                  heightOfText = sizeInfoText.getHeight();

                Double widthOfChord = (double) 0;
                if (nextLinePart.getChord() != null && !nextLinePart.getChord().trim()
                    .isEmpty() && exportConfiguration.isWithChords()) {

                  String eventuallyTransformedChord = transposeChordOnDemand(nextLinePart, nextSong,
                      exportConfiguration);

                  SizeInfo sizeinfoChord = documentBuilder.getSize(eventuallyTransformedChord, ExportTokenType.CHORD);
                  documentBuilder.newToken(new ExportToken(nextSong, nextStructItem, eventuallyTransformedChord,
                      new AreaInfo(locationInfoChord, sizeinfoChord), ExportTokenType.CHORD));

                  widthOfChord = sizeinfoChord.getWidth() + exportConfiguration.getMinimalChordDistance();
                }

                documentBuilder.newToken(new ExportToken(nextSong, nextStructItem, nextLinePart.getText(),
                    new AreaInfo(locationInfoText, sizeInfoText), ExportTokenType.TEXT));

                Double maximumLength = Double.max(widthOfChord, widthOfText);
                if (nextLine.getText() == null || nextLine.getText().trim().isEmpty())
                  maximumLength = Double.max(maximumLength,
                      exportConfiguration.getMinimalChordDistance() + widthOfChord);
                if (locationInfoChord != null)
                  locationInfoChord = locationInfoCalculator.addX(locationInfoChord, maximumLength);
                locationInfoText = locationInfoCalculator.addX(locationInfoText, maximumLength);
              }
            } else { //to avoid empty gaps
              SizeInfo sizeInfoText = documentBuilder.getSize(nextLine.getText(), ExportTokenType.TEXT);
              heightOfText = sizeInfoText.getHeight();
              documentBuilder.newToken(new ExportToken(nextSong, nextStructItem, nextLine.getText(),
                  new AreaInfo(locationInfoText, sizeInfoText), ExportTokenType.TEXT));
            }

            Double interLineDistance = exportConfiguration.getInterLineDistance();

            locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(),
                locationInfoText.getY() + heightOfText + interLineDistance);

            if (documentBuilder.getPageSize() != null) {
              if (!nextLine.equals(nextPart.getLastLine()) && locationInfo.getY() > (documentBuilder.getPageSize()
                  .getHeight() - exportConfiguration.getLowerBorder())) {
                documentBuilder.newToken(new ExportTokenNewPage(nextSong));
                locationInfo = new LocationInfo(exportConfiguration.getLeftBorder(),
                    exportConfiguration.getUpperBorder());
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

    if (exportFile != null && exportFile.exists()) {
      exportFile.setWritable(true);
      exportFile.setExecutable(false);

      LOGGER.info("Presentation created successfully in " + exportFile.getAbsolutePath());
    }
  }

  private String transposeChordOnDemand(LinePart linePart, Song song, ExportConfiguration mergedConfiguration) {
    return linePart.getChord(mergedConfiguration.getKeyType());
  }

  /**
   * get chord, which need the most y
   *
   * @param documentBuilder
   * @param line
   * @return
   */
  private Double getHighestChord(final DocumentBuilder documentBuilder, final Line line, Song song,
      ExportConfiguration mergedConfiguration) {
    Double maxChordHeight = 0d;
    for (LinePart next : line.getLineParts()) {
      if (next.getChord() != null && !next.getChord().trim().isEmpty()) {
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
   *
   * @param documentBuilder     documentbuilder
   * @param song                song
   * @param mergedConfiguration mergedConfiguration
   * @return longest structure text
   */
  private Double getLongestStructureText(final DocumentBuilder documentBuilder, final Song song,
      ExportConfiguration mergedConfiguration) {

    if (mergedConfiguration.getSongPartDescriptorType() == null || mergedConfiguration.getSongPartDescriptorType()
        .equals(SongPartDescriptorStrategy.NONE))
      return Double.valueOf(0);

    Double maxStructureWidth = 0d;

    SongInfoService songInfoService = new SongInfoService();

    for (SongStructItem next : song.getStructItems()) {
      String structureText = songInfoService.getStructure(song, next, mergedConfiguration.getSongPartDescriptorType());
      Double currentWeight = documentBuilder.getSize(structureText, ExportTokenType.STRUCTURE).getWidth();
      if (currentWeight > maxStructureWidth)
        maxStructureWidth = currentWeight + mergedConfiguration.getStructureDistance();
    }

    return maxStructureWidth;

  }
}
