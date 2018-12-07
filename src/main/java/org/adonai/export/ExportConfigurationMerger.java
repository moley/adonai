package org.adonai.export;

public class ExportConfigurationMerger {

  public ExportConfiguration getMergedExportConfiguration (final ExportConfiguration defaultConfiguration,
                                                           final ExportConfiguration userConfiguration) {

    if (defaultConfiguration.getDocumentBuilderClass() != null &&
      userConfiguration.getDocumentBuilderClass() != null &&
      ! defaultConfiguration.getDocumentBuilderClass().equals(userConfiguration.getDocumentBuilderClass()))
      throw new IllegalStateException("You cannot merge export configurations with different document builder classes " + defaultConfiguration.getDocumentBuilderClass() + " and " + userConfiguration.getDocumentBuilderClass());

    ExportConfiguration mergedConfiguration = new ExportConfiguration();
    mergedConfiguration.setNewPageStrategy(userConfiguration.getNewPageStrategy() != null ? userConfiguration.getNewPageStrategy() : defaultConfiguration.getNewPageStrategy());
    mergedConfiguration.setPageSize(userConfiguration.getPageSize() != null ? userConfiguration.getPageSize() : defaultConfiguration.getPageSize());
    mergedConfiguration.setWithChords(userConfiguration.isWithChords() != null ? userConfiguration.isWithChords() : defaultConfiguration.isWithChords());
    mergedConfiguration.setWithTitle(userConfiguration.getWithTitle() != null ? userConfiguration.getWithTitle(): defaultConfiguration.getWithTitle());
    mergedConfiguration.setWithPartType(userConfiguration.isWithPartType() != null ? userConfiguration.isWithPartType() : defaultConfiguration.isWithPartType());
    mergedConfiguration.setInterPartDistance(userConfiguration.getInterPartDistance() != null ? userConfiguration.getInterPartDistance() : defaultConfiguration.getInterPartDistance());
    mergedConfiguration.setInterSongDistance(userConfiguration.getInterSongDistance() != null ? userConfiguration.getInterSongDistance() : defaultConfiguration.getInterSongDistance());
    mergedConfiguration.setInterLineDistance(userConfiguration.getInterLineDistance() != null ? userConfiguration.getInterLineDistance() : defaultConfiguration.getInterLineDistance());
    mergedConfiguration.setChordTextDistance(userConfiguration.getChordTextDistance() != null ? userConfiguration.getChordTextDistance() : defaultConfiguration.getChordTextDistance());
    mergedConfiguration.setUpperBorder(userConfiguration.getUpperBorder() != null ? userConfiguration.getUpperBorder(): defaultConfiguration.getUpperBorder());
    mergedConfiguration.setLeftBorder(userConfiguration.getLeftBorder() != null ? userConfiguration.getLeftBorder(): defaultConfiguration.getLeftBorder());
    mergedConfiguration.setMinimalChordDistance(userConfiguration.getMinimalChordDistance() != null ? userConfiguration.getMinimalChordDistance(): defaultConfiguration.getMinimalChordDistance());
    mergedConfiguration.setOpenPreview(userConfiguration.isOpenPreview() != null ? userConfiguration.isOpenPreview(): defaultConfiguration.isOpenPreview());
    mergedConfiguration.setDocumentBuilderClass(defaultConfiguration.getDocumentBuilderClass());



    return mergedConfiguration;
  }
}
