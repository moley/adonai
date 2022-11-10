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
    merged.setReferenceStrategy(userConf.getReferenceStrategy() != null ? userConf.getReferenceStrategy() : defConf.getReferenceStrategy());
    merged.setPageSize(userConf.getPageSize() != null ? userConf.getPageSize() : defConf.getPageSize());
    merged.setWithChords(userConf.isWithChordsInitialized() ? userConf.isWithChords() : defConf.isWithChords());
    merged.setWithContentPage(userConf.isWithContentPageInitialized() ? userConf.isWithContentPage(): defConf.isWithContentPage());
    merged.setWithIndexPage(userConf.isWithIndexPageInitialized() ? userConf.isWithIndexPage(): defConf.isWithIndexPage());
    merged.setWithTitle(userConf.isWithTitleInitialized() ? userConf.getWithTitle(): defConf.getWithTitle());
    merged.setRemarksRight(userConf.isRemarksRight() ? userConf.isRemarksRight() : defConf.isRemarksRight());
    merged.setInterPartDistance(userConf.getInterPartDistance() >=0 ? userConf.getInterPartDistance() : defConf.getInterPartDistance());
    merged.setInterSongDistance(userConf.getInterSongDistance() >=0 ? userConf.getInterSongDistance() : defConf.getInterSongDistance());
    merged.setInterLineDistance(userConf.getInterLineDistance() >=0 ? userConf.getInterLineDistance() : defConf.getInterLineDistance());
    merged.setStructureDistance(userConf.getStructureDistance() >=0 ? userConf.getStructureDistance() : defConf.getStructureDistance());
    merged.setChordTextDistance(userConf.getChordTextDistance() >=0 ? userConf.getChordTextDistance() : defConf.getChordTextDistance());
    merged.setTitleSongDistance(userConf.getTitleSongDistance() >=0 ? userConf.getTitleSongDistance() : defConf.getTitleSongDistance());
    merged.setUpperBorder(userConf.getUpperBorder() >=0 ? userConf.getUpperBorder(): defConf.getUpperBorder());
    merged.setLeftBorder(userConf.getLeftBorder() >=0 ? userConf.getLeftBorder(): defConf.getLeftBorder());
    merged.setLowerBorder(userConf.getLowerBorder() >=0 ? userConf.getLowerBorder(): defConf.getLowerBorder());
    merged.setMinimalChordDistance(userConf.getMinimalChordDistance() >=0 ? userConf.getMinimalChordDistance(): defConf.getMinimalChordDistance());
    merged.setRemarksStructureDistance(userConf.getRemarksStructureDistance() >= 0 ? userConf.getRemarksStructureDistance(): defConf.getRemarksStructureDistance());
    merged.setOpenPreview(userConf.isOpenPreviewInitialized() ? userConf.isOpenPreview(): defConf.isOpenPreview());
    merged.setDocumentBuilderClass(defConf.getDocumentBuilderClass());

    return merged;
  }


}
