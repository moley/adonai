package org.adonai.export;

public class ExportConfigurationMerger {

  public ExportConfiguration getMergedExportConfiguration (final ExportConfiguration defConf,
                                                           final ExportConfiguration userConf) {

    if (defConf.getDocumentBuilderClass() != null &&
      userConf.getDocumentBuilderClass() != null &&
      ! defConf.getDocumentBuilderClass().equals(userConf.getDocumentBuilderClass()))
      throw new IllegalStateException("You cannot merge export configurations with different document builder classes " + defConf.getDocumentBuilderClass() + " and " + userConf.getDocumentBuilderClass());

    ExportConfiguration merged = new ExportConfiguration();
    merged.setSongPartDescriptorType(userConf.getSongPartDescriptorType() != null ? userConf.getSongPartDescriptorType(): defConf.getSongPartDescriptorType());
    merged.setNewPageStrategy(userConf.getNewPageStrategy() != null ? userConf.getNewPageStrategy() : defConf.getNewPageStrategy());
    merged.setPageSize(userConf.getPageSize() != null ? userConf.getPageSize() : defConf.getPageSize());
    merged.setWithChords(userConf.isWithChords() != null ? userConf.isWithChords() : defConf.isWithChords());
    merged.setWithTitle(userConf.getWithTitle() != null ? userConf.getWithTitle(): defConf.getWithTitle());
    merged.setWithPartType(userConf.isWithPartType() != null ? userConf.isWithPartType() : defConf.isWithPartType());
    merged.setInterPartDistance(userConf.getInterPartDistance() != null ? userConf.getInterPartDistance() : defConf.getInterPartDistance());
    merged.setInterSongDistance(userConf.getInterSongDistance() != null ? userConf.getInterSongDistance() : defConf.getInterSongDistance());
    merged.setInterLineDistance(userConf.getInterLineDistance() != null ? userConf.getInterLineDistance() : defConf.getInterLineDistance());
    merged.setStructureDistance(userConf.getStructureDistance() != null ? userConf.getStructureDistance() : defConf.getStructureDistance());
    merged.setChordTextDistance(userConf.getChordTextDistance() != null ? userConf.getChordTextDistance() : defConf.getChordTextDistance());
    merged.setTitleSongDistance(userConf.getTitleSongDistance() != null ? userConf.getTitleSongDistance() : defConf.getTitleSongDistance());
    merged.setUpperBorder(userConf.getUpperBorder() != null ? userConf.getUpperBorder(): defConf.getUpperBorder());
    merged.setLeftBorder(userConf.getLeftBorder() != null ? userConf.getLeftBorder(): defConf.getLeftBorder());
    merged.setLowerBorder(userConf.getLowerBorder() != null ? userConf.getLowerBorder(): defConf.getLowerBorder());
    merged.setMinimalChordDistance(userConf.getMinimalChordDistance() != null ? userConf.getMinimalChordDistance(): defConf.getMinimalChordDistance());
    merged.setOpenPreview(userConf.isOpenPreview() != null ? userConf.isOpenPreview(): defConf.isOpenPreview());
    merged.setDocumentBuilderClass(defConf.getDocumentBuilderClass());

    return merged;
  }
}
